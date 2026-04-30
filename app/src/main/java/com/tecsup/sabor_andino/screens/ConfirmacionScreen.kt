package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmacionScreen(
    numeroPedido     : String,
    tipoEntrega      : String,
    numeroMesa       : String,
    onVolverAlInicio : () -> Unit
) {
    val descripcionEntrega = when (tipoEntrega) {
        "mesa"   -> "Mesa $numeroMesa"
        "llevar" -> "Para llevar"
        else     -> ""
    }

    Column(
        modifier            = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✅", style = MaterialTheme.typography.displayLarge)

        Spacer(Modifier.height(24.dp))

        Text("¡Pedido confirmado!", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        // Datos del pedido: número + tipo de entrega
        Text("Pedido: $numeroPedido", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(descripcionEntrega, style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(12.dp))
        Text(
            "Tu pedido ha sido recibido.\nPronto estará listo.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick  = onVolverAlInicio,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
