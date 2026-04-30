package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

@Composable
fun PedidoScreen(
    pedidoViewModel : PedidoViewModel,
    onAtras         : () -> Unit,
    onIrACheckout   : () -> Unit
) {
    val pedidos by pedidoViewModel.pedido.collectAsState()
    val total    = pedidos.sumOf { it.plato.precio * it.cantidad }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        TextButton(onClick = onAtras) { Text("← Atrás") }

        Spacer(Modifier.height(12.dp))
        Text("Mi Carrito", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (pedidos.isEmpty()) {
            Text("Tu carrito está vacío.")
        } else {

            LazyColumn(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pedidos, key = { it.plato.id }) { item ->
                    Card(
                        shape    = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.CenterVertically
                            ) {
                                Text(
                                    "${item.plato.emoji} ${item.plato.nombre}",
                                    style    = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                // Eliminar ítem completo
                                TextButton(onClick = { pedidoViewModel.eliminarDelPedido(item.plato.id) }) {
                                    Text("✕")
                                }
                            }

                            // Nota del plato (si la tiene)
                            if (item.nota.isNotBlank()) {
                                Text(
                                    "📝 ${item.nota}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            // +/- de cantidad directamente en el carrito
                            Row(
                                verticalAlignment     = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier              = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    TextButton(onClick = { pedidoViewModel.modificarCantidad(item.plato.id, -1) }) {
                                        Text("−")
                                    }
                                    Text(
                                        item.cantidad.toString(),
                                        style    = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(horizontal = 12.dp)
                                    )
                                    TextButton(onClick = { pedidoViewModel.modificarCantidad(item.plato.id, +1) }) {
                                        Text("+")
                                    }
                                }
                                Text(
                                    "S/ ${"%.2f".format(item.plato.precio * item.cantidad)}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Total: S/ ${"%.2f".format(total)}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            // Va a Checkout (elige mesa o para llevar) en lugar de confirmar directamente
            Button(
                onClick  = onIrACheckout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a pagar")
            }
        }
    }
}
