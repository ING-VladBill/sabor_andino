package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

@Composable
fun PedidoScreen(
    pedidoViewModel: PedidoViewModel,
    onAtras: () -> Unit
) {
    val pedidos by pedidoViewModel.pedido.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        TextButton(onClick = onAtras) {
            Text("← Atrás")
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Mi Pedido",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        if (pedidos.isEmpty()) {

            Text("No tienes platos en tu pedido aún.")

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pedidos) { item ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "${item.plato.emoji} ${item.plato.nombre}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            Text("Cantidad: ${item.cantidad}")
                            Text("Subtotal: S/ ${item.plato.precio * item.cantidad}")
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Total: S/ ${pedidoViewModel.calcularTotal()}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: lógica de finalizar pedido */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar Pedido")
            }
        }
    }
}