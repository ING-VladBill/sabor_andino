package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.sabor_andino.R
import com.tecsup.sabor_andino.data.PlatosRepository

@Composable
fun MenuScreen(
    onPlatoClick: (Int) -> Unit,
    onAtras: () -> Unit,
    onVerCarrito: () -> Unit,
    cantidadEnPedido: Int,
    totalPedido: Double,
    cantidadDeItem: (platoId: Int) -> Int,
    onAgregarAlCarrito: (com.tecsup.sabor_andino.data.Plato) -> Unit
) {
    val categorias = listOf("Todos", "Entrada", "Plato de Fondo", "Postre", "Bebida")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    val platosFiltrados = remember(categoriaSeleccionada) {
        if (categoriaSeleccionada == "Todos") PlatosRepository.platos
        else PlatosRepository.platos.filter { it.categoria == categoriaSeleccionada }
    }

    val imagenesPorPlato = mapOf(
        1 to R.drawable.img_trucha,
        2 to R.drawable.img_papa_huancaina,
        3 to R.drawable.img_pachamanca,
        4 to R.drawable.img_cuy_chactado,
        5 to R.drawable.img_mazamorra_quinua,
        6 to R.drawable.img_mate_coca
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // CAPA 1 — Fondo:
        Image(
            painter = painterResource(R.drawable.fondo_menu),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // CAPA 2 — Contenido:
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                // — BANNER INFERIOR fijo:
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E8D8)),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFC0622B))
                        ) {
                            Icon(
                                Icons.Default.OutdoorGrill, null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text(
                                "Hecho con ingredientes frescos",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Seleccionados cada día para ti",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        // — Ícono de hoja decorativo a la derecha:
                        Icon(
                            Icons.Default.Eco, null,
                            tint = Color(0xFF8B4513).copy(alpha = 0.5f),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            },
            floatingActionButton = {
                if (cantidadEnPedido > 0) {
                    ExtendedFloatingActionButton(
                        onClick = onVerCarrito,
                        containerColor = Color(0xFF2D4A2D),
                        contentColor = Color.White,
                        icon = { Icon(Icons.Default.ShoppingBag, null) },
                        text = { Text("$cantidadEnPedido · S/ ${"%.2f".format(totalPedido)}") }
                    )
                }
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {

                // ÍTEM 1 — Header (TopBar + título + logo):
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {

                        // — Botón Atrás:
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .clickable { onAtras() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color(0xFF2D4A2D)
                            )
                            Text(
                                " Atrás", style = MaterialTheme.typography.bodyLarge,
                                color = Color(0xFF2D4A2D)
                            )
                        }

                    }

                    // — Título y subtítulo:
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Menú", style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.ExtraBold, color = Color(0xFF2D4A2D)
                            )
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                Icons.Default.Restaurant, null,
                                tint = Color(0xFF8B4513), modifier = Modifier.size(28.dp)
                            )
                        }
                        // — Línea naranja decorativa bajo el título:
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(2.dp)
                                .background(Color(0xFF8B4513))
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Descubre nuestros sabores andinos",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }

                // ÍTEM 2 — Filtros de categoría:
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categorias) { categoria ->
                            FilterChip(
                                selected = categoriaSeleccionada == categoria,
                                onClick = { categoriaSeleccionada = categoria },
                                label = { Text(categoria) },
                                shape = RoundedCornerShape(50),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFC0622B),
                                    selectedLabelColor = Color.White,
                                    containerColor = Color.White,
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = Color(0xFFDDDDDD),
                                    selectedBorderColor = Color(0xFFC0622B),
                                    enabled = true, selected = categoriaSeleccionada == categoria
                                )
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }

                // ÍTEMS 3..N — Lista de platos:
                items(platosFiltrados, key = { it.id }) { plato ->
                    val imagenRes = imagenesPorPlato[plato.id]
                    val enCarrito = cantidadDeItem(plato.id)

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .clickable { onPlatoClick(plato.id) },
                            verticalAlignment = Alignment.Top
                        ) {
                            // — Imagen del plato:
                            Box {
                                if (imagenRes != null) {
                                    Image(
                                        painter = painterResource(imagenRes),
                                        contentDescription = plato.nombre,
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    // — Fallback si no hay imagen:
                                    Box(
                                        Modifier
                                            .size(90.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFF5EDE3))
                                    ) {
                                        Text(
                                            plato.emoji, fontSize = 36.sp,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }
                                
                                // Badge de cantidad si ya está en el carrito
                                if (enCarrito > 0) {
                                    Badge(
                                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp),
                                        containerColor = Color(0xFF2D4A2D),
                                        contentColor = Color.White
                                    ) {
                                        Text(enCarrito.toString())
                                    }
                                }
                            }

                            // — Nombre y descripción:
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(plato.emoji + " ", fontSize = 16.sp)
                                    Text(
                                        plato.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    plato.descripcionCorta,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // — Precio y botón +:
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.height(90.dp)
                            ) {
                                Text(
                                    "S/ ${plato.precio}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC0622B)
                                )
                                Box(
                                    Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2D4A2D))
                                        .clickable { onAgregarAlCarrito(plato) }
                                ) {
                                    Icon(
                                        Icons.Default.Add, null,
                                        tint = Color.White,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(20.dp)
                                    )
                                }
                            }
                        }

                        // — Divider entre platos:
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFEEEEEE),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
        // — Logo en esquina superior derecha:
        Image(
            painter = painterResource(R.drawable.logo_sabor_andino),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (24).dp, y = (-80).dp)
                .size(270.dp)
        )
    }
}
