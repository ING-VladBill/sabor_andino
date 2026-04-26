package com.tecsup.sabor_andino.viewmodel


import androidx.lifecycle.ViewModel
import com.tecsup.sabor_andino.data.Plato
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ItemPedido(val plato: Plato, val cantidad: Int)

class PedidoViewModel : ViewModel() {

    private val _pedido = MutableStateFlow<List<ItemPedido>>(emptyList())
    val pedido: StateFlow<List<ItemPedido>> = _pedido.asStateFlow()

    fun agregarAlPedido(plato: Plato, cantidad: Int) {
        val actual = _pedido.value.toMutableList()
        val existente = actual.indexOfFirst { it.plato.id == plato.id }

        if (existente >= 0) {
            actual[existente] = actual[existente].copy(
                cantidad = actual[existente].cantidad + cantidad
            )
        } else {
            actual.add(ItemPedido(plato, cantidad))
        }
        _pedido.value = actual
    }

    fun calcularTotal(): Double {
        return _pedido.value.sumOf { it.plato.precio * it.cantidad }
    }
}