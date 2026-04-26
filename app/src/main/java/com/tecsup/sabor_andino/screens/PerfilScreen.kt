package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

@Composable
fun PerfilScreen(
    pedidoViewModel: PedidoViewModel,
    onAtras:()->Unit
){

    val pedidos by pedidoViewModel.pedido.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        TextButton(onClick = onAtras){
            Text("← Atrás")
        }

        Spacer(Modifier.height(12.dp))


        // -------- PERFIL --------

        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Spacer(Modifier.width(16.dp))

                Column{

                    Text(
                        text="Gabriel Llanos",
                        style=MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text="Cliente frecuente"
                    )

                }

            }

        }

        Spacer(Modifier.height(28.dp))


        // -------- MI PEDIDO --------

        Text(
            text = "Mi Pedido",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(12.dp))


        if(pedidos.isEmpty()){

            Text("No tienes pedidos agregados.")

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ){

                items(pedidos){ item ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ){

                        Column(
                            Modifier.padding(16.dp)
                        ){

                            Text(
                                "${item.plato.emoji} ${item.plato.nombre}"
                            )

                            Text(
                                "Cantidad: ${item.cantidad}"
                            )

                            Text(
                                "Subtotal: S/ ${
                                    item.plato.precio * item.cantidad
                                }"
                            )

                        }

                    }

                }

            }

            Spacer(Modifier.height(20.dp))

            Text(
                text="Total: S/ ${pedidoViewModel.calcularTotal()}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Finalizar Pedido")
            }

        }

    }

}