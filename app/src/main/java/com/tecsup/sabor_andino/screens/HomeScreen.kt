package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    correo: String,
    cantidadEnPedido: Int,
    onVerMenu: () -> Unit,
    onVerPedido: () -> Unit,
    onVerPerfil: () -> Unit
) {
    val nombre = correo.substringBefore("@").replaceFirstChar { it.uppercase() }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {

        Spacer(modifier = Modifier.height(80.dp))

        Text("Hola, $nombre", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onVerMenu, modifier = Modifier.fillMaxWidth()) {
            Text("Ver Menú")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onVerPedido, modifier = Modifier.fillMaxWidth()) {
            // Muestra cuántos items distintos tiene el pedido
            val etiqueta = if (cantidadEnPedido > 0) "Mi Pedido ($cantidadEnPedido)" else "Mi Pedido"
            Text(etiqueta)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onVerPerfil, modifier = Modifier.fillMaxWidth()) {
            Text("Mi Perfil")
        }
    }
}
