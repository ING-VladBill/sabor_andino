package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.sabor_andino.data.PlatosRepository
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

@Composable
fun DetalleScreen(
    platoId           : Int,
    pedidoViewModel   : PedidoViewModel,
    onAtras           : () -> Unit,
    onPlatoAgregado   : () -> Unit,   // popBackStack → vuelve al menú
    onVerCarrito      : () -> Unit,   // va directo al carrito (FAB)
    cantidadEnPedido  : Int,
    totalPedido       : Double,
    cantidadEnCarrito : Int           // cuántos de ESTE plato ya están en el carrito
) {
    val plato = PlatosRepository.platos.first { it.id == platoId }
    var cantidad by remember { mutableIntStateOf(1) }
    var nota     by remember { mutableStateOf("") }

    Scaffold(
        // FAB del carrito: igual que en Menú, siempre accesible
        floatingActionButton = {
            if (cantidadEnPedido > 0) {
                FloatingActionButton(onClick = onVerCarrito) {
                    Text("🛒 $cantidadEnPedido  ·  S/ ${"%.2f".format(totalPedido)}")
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            TextButton(onClick = onAtras) { Text("← Atrás") }

            Spacer(Modifier.height(16.dp))
            Text("${plato.emoji} ${plato.nombre}", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(4.dp))
            Text("Categoría: ${plato.categoria}", style = MaterialTheme.typography.labelLarge)

            Spacer(Modifier.height(12.dp))
            Text(plato.descripcionCompleta)

            Spacer(Modifier.height(20.dp))
            Text("Precio: S/ ${plato.precio}", style = MaterialTheme.typography.titleMedium)

            // Indicador: ya tenías este plato agregado antes
            if (cantidadEnCarrito > 0) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text  = "Ya tienes $cantidadEnCarrito en tu carrito",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(24.dp))

            // Selector de cantidad
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { if (cantidad > 1) cantidad-- }) { Text("-") }
                Spacer(Modifier.width(20.dp))
                Text(cantidad.toString(), style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.width(20.dp))
                Button(onClick = { cantidad++ }) { Text("+") }
            }

            Spacer(Modifier.height(12.dp))
            Text(
                "Subtotal: S/ ${"%.2f".format(plato.precio * cantidad)}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(20.dp))

            // Nota opcional por plato (sin cebolla, término 3/4, etc.)
            OutlinedTextField(
                value         = nota,
                onValueChange = { nota = it },
                label         = { Text("Nota (opcional): sin cebolla, término 3/4…") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    pedidoViewModel.agregarAlPedido(plato, cantidad, nota)
                    onPlatoAgregado()   // vuelve al menú para seguir eligiendo
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar al Pedido")
            }
        }
    }
}
