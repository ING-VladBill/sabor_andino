package com.tecsup.sabor_andino.viewmodel

import androidx.lifecycle.ViewModel
import com.tecsup.sabor_andino.data.Plato
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

data class ItemPedido(
    val plato: Plato,
    val cantidad: Int,
    val nota: String = ""      // "sin cebolla", "término 3/4", etc.
)

data class PedidoCompletado(
    val id: String,
    val items: List<ItemPedido>,
    val total: Double,
    val fecha: String,
    val tipoEntrega: String
)

class PedidoViewModel : ViewModel() {

    private val _pedido = MutableStateFlow<List<ItemPedido>>(emptyList())
    val pedido: StateFlow<List<ItemPedido>> = _pedido.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo.asStateFlow()

    // Datos del último pedido confirmado (para mostrar en Confirmación)
    private val _numeroPedido = MutableStateFlow("")
    val numeroPedido: StateFlow<String> = _numeroPedido.asStateFlow()

    private val _tipoEntrega = MutableStateFlow("")
    val tipoEntrega: StateFlow<String> = _tipoEntrega.asStateFlow()

    private val _numeroMesa = MutableStateFlow("")
    val numeroMesa: StateFlow<String> = _numeroMesa.asStateFlow()

    private val _historialPedidos = MutableStateFlow<List<PedidoCompletado>>(emptyList())
    val historialPedidos: StateFlow<List<PedidoCompletado>> = _historialPedidos.asStateFlow()

    fun setCorreo(correo: String) { _correo.value = correo }

    fun agregarAlPedido(plato: Plato, cantidad: Int, nota: String = "") {
        val actual = _pedido.value.toMutableList()
        val idx = actual.indexOfFirst { it.plato.id == plato.id }
        if (idx >= 0) {
            // Si ya existe: suma cantidad y actualiza nota si viene una nueva
            actual[idx] = actual[idx].copy(
                cantidad = actual[idx].cantidad + cantidad,
                nota     = nota.ifBlank { actual[idx].nota }
            )
        } else {
            actual.add(ItemPedido(plato, cantidad, nota))
        }
        _pedido.value = actual
    }

    // +1 o -1 directamente desde el carrito; si llega a 0 elimina el ítem
    fun modificarCantidad(platoId: Int, delta: Int) {
        val actual = _pedido.value.toMutableList()
        val idx = actual.indexOfFirst { it.plato.id == platoId }
        if (idx >= 0) {
            val nueva = actual[idx].cantidad + delta
            if (nueva <= 0) actual.removeAt(idx)
            else actual[idx] = actual[idx].copy(cantidad = nueva)
            _pedido.value = actual
        }
    }

    fun eliminarDelPedido(platoId: Int) {
        _pedido.value = _pedido.value.filter { it.plato.id != platoId }
    }

    // Cuántos de un plato específico hay en el carrito (para badge en lista y detalle)
    fun cantidadDeItem(platoId: Int): Int =
        _pedido.value.find { it.plato.id == platoId }?.cantidad ?: 0

    fun calcularTotal(): Double = _pedido.value.sumOf { it.plato.precio * it.cantidad }

    // Se llama desde Checkout al confirmar
    fun confirmarPedido(tipo: String, mesa: String) {
        val numPedido = "#${(1000..9999).random()}"
        val total = calcularTotal()
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val entregaDesc = if (tipo == "mesa") "Mesa $mesa" else "Para llevar"

        val pedidoCompletado = PedidoCompletado(
            id = numPedido,
            items = _pedido.value.toList(),
            total = total,
            fecha = fechaActual,
            tipoEntrega = entregaDesc
        )

        // Guardar en historial antes de limpiar
        _historialPedidos.value = listOf(pedidoCompletado) + _historialPedidos.value

        _tipoEntrega.value = tipo
        _numeroMesa.value  = mesa
        _numeroPedido.value = numPedido
        _pedido.value = emptyList()
    }

    fun cerrarSesion() {
        _correo.value = ""
        _pedido.value = emptyList()
        _historialPedidos.value = emptyList()
        _numeroPedido.value = ""
        _tipoEntrega.value = ""
        _numeroMesa.value = ""
    }
}
