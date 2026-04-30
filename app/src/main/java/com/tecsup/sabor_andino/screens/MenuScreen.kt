package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.sabor_andino.data.PlatosRepository

@Composable
fun MenuScreen(
    onPlatoClick     : (Int) -> Unit,
    onAtras          : () -> Unit,
    onVerCarrito     : () -> Unit,
    cantidadEnPedido : Int,
    totalPedido      : Double,
    cantidadDeItem   : (platoId: Int) -> Int     // cuántos de cada plato hay en el carrito
) {
    val categorias = listOf("Todos", "Entrada", "Plato de Fondo", "Postre", "Bebida")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    val platosFiltrados = remember(categoriaSeleccionada) {
        if (categoriaSeleccionada == "Todos") PlatosRepository.platos
        else PlatosRepository.platos.filter { it.categoria == categoriaSeleccionada }
    }

    Scaffold(
        // FAB del carrito: solo aparece cuando hay algo agregado; flota sobre el scroll
        floatingActionButton = {
            if (cantidadEnPedido > 0) {
                FloatingActionButton(onClick = onVerCarrito) {
                    Text("🛒 $cantidadEnPedido  ·  S/ ${"%.2f".format(totalPedido)}")
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TextButton(onClick = onAtras) { Text("← Atrás") }

            Text("Menú", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            // Filtros de categoría desplazables
            Row(
                modifier              = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { cat ->
                    FilterChip(
                        selected = categoriaSeleccionada == cat,
                        onClick  = { categoriaSeleccionada = cat },
                        label    = { Text(cat) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(platosFiltrados, key = { it.id }) { plato ->
                    val enCarrito = cantidadDeItem(plato.id)

                    Button(
                        onClick  = { onPlatoClick(plato.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Badge inline: muestra cuántos hay en el carrito
                        val badge = if (enCarrito > 0) "  [×$enCarrito]" else ""
                        Text("${plato.emoji} ${plato.nombre} — S/ ${plato.precio}$badge")
                    }
                }
            }
        }
    }
}
