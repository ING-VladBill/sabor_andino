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
    platoId:Int,
    pedidoViewModel: PedidoViewModel,
    onAtras:()->Unit,
    onIrPerfil:()->Unit
) {

    val plato = PlatosRepository.platos.first { it.id == platoId }

    var cantidad by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ){

        TextButton(onClick=onAtras){
            Text("← Atrás")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "${plato.emoji} ${plato.nombre}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = plato.descripcionCompleta
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Precio: S/ ${plato.precio}"
        )

        Spacer(Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){

            Button(
                onClick = {
                    if(cantidad>1) cantidad--
                }
            ){
                Text("-")
            }

            Spacer(Modifier.width(20.dp))

            Text(
                text = cantidad.toString(),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.width(20.dp))

            Button(
                onClick = {
                    cantidad++
                }
            ){
                Text("+")
            }

        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Subtotal: S/ ${plato.precio * cantidad}"
        )

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = {
                pedidoViewModel.agregarAlPedido(
                    plato,
                    cantidad
                )
                onIrPerfil()
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Agregar al Pedido")
        }

    }
}