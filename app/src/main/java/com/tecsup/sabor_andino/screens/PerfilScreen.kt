package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    pedidoViewModel: PedidoViewModel,  // ✅ lee el correo real desde el ViewModel
    onAtras: () -> Unit
) {
    val correo by pedidoViewModel.correo.collectAsState()
    val nombre = correo.substringBefore("@").replaceFirstChar { it.uppercase() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextButton(onClick = onAtras) { Text("← Atrás") }

        Spacer(Modifier.height(12.dp))
        Text("Mi Perfil", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(20.dp))

        Card(
            shape    = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier          = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(nombre, style = MaterialTheme.typography.titleLarge)
                    Text("Cliente")
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Card(
            shape    = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Información de contacto", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                Text("📧 $correo")
            }
        }
    }
}
