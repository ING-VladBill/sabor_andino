package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutScreen(
    totalPedido : Double,
    onAtras     : () -> Unit,
    onConfirmar : (tipo: String, mesa: String) -> Unit
) {
    // "mesa" o "llevar" — vacío hasta que el usuario elija
    var tipoSeleccionado by remember { mutableStateOf("") }
    var numeroMesa       by remember { mutableStateOf("") }
    var error            by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {

        TextButton(onClick = onAtras) { Text("← Atrás") }

        Spacer(Modifier.height(12.dp))
        Text("¿Cómo recibirás tu pedido?", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(32.dp))

        // Opción: en mesa
        Button(
            onClick  = { tipoSeleccionado = "mesa"; error = "" },
            modifier = Modifier.fillMaxWidth()
        ) {
            val marca = if (tipoSeleccionado == "mesa") "✓  " else ""
            Text("${marca}Comer en mesa")
        }

        Spacer(Modifier.height(12.dp))

        // Opción: para llevar
        Button(
            onClick  = { tipoSeleccionado = "llevar"; numeroMesa = ""; error = "" },
            modifier = Modifier.fillMaxWidth()
        ) {
            val marca = if (tipoSeleccionado == "llevar") "✓  " else ""
            Text("${marca}Para llevar")
        }

        // Campo de número de mesa: solo aparece si eligió "en mesa"
        if (tipoSeleccionado == "mesa") {
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value          = numeroMesa,
                onValueChange  = { numeroMesa = it; error = "" },
                label          = { Text("Número de mesa") },
                modifier       = Modifier.fillMaxWidth(),
                singleLine     = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        if (error.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        // Empuja el resumen y el botón al fondo de la pantalla
        Spacer(Modifier.weight(1f))

        Text(
            "Total a pagar: S/ ${"%.2f".format(totalPedido)}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    tipoSeleccionado.isEmpty()                    -> error = "Selecciona cómo recibirás tu pedido"
                    tipoSeleccionado == "mesa" && numeroMesa.isBlank() -> error = "Ingresa el número de mesa"
                    else -> onConfirmar(tipoSeleccionado, numeroMesa)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar pedido")
        }
    }
}
