package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    correo: String,
    onVerMenu: () -> Unit,
    onVerPerfil: () -> Unit
) {
    val nombre = correo.substringBefore("@")

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {

        Spacer(modifier = Modifier.height(80.dp))

        Text("Hola, $nombre")
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onVerMenu, modifier = Modifier.fillMaxWidth()) {
            Text("Ver Menú")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onVerPerfil, modifier = Modifier.fillMaxWidth()) {
            Text("Mi Pedido")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onVerPerfil, modifier = Modifier.fillMaxWidth()) {
            Text("Mi Perfil")
        }
    }
}