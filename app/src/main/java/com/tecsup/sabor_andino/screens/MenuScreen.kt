package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.sabor_andino.data.PlatosRepository

@Composable
fun MenuScreen(onPlatoClick: (Int) -> Unit, onAtras: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {

        TextButton(onClick = onAtras) { Text("← Atrás") }
        Text("Menú")
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(PlatosRepository.platos) { plato ->
                Button(
                    onClick = { onPlatoClick(plato.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("${plato.nombre} — S/ ${plato.precio}")
                }
            }
        }
    }
}