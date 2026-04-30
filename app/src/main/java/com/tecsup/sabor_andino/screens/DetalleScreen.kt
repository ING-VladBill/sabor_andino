package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.sabor_andino.R
import com.tecsup.sabor_andino.data.PlatosRepository
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

@Composable
fun DetalleScreen(
    platoId           : Int,
    pedidoViewModel   : PedidoViewModel,
    onAtras           : () -> Unit,
    onPlatoAgregado   : () -> Unit,
    onVerCarrito      : () -> Unit,
    cantidadEnPedido  : Int,
    totalPedido       : Double,
    cantidadEnCarrito : Int
) {
    val plato = PlatosRepository.platos.first { it.id == platoId }
    var cantidad by remember { mutableIntStateOf(1) }
    var nota     by remember { mutableStateOf("") }

    val fondosPorPlato = mapOf(
        1 to R.drawable.fondo_trucha,
        2 to R.drawable.fondo_papa_huancaina,
        3 to R.drawable.fondo_pachamanca,
        4 to R.drawable.fondo_cuy_chactado,
        5 to R.drawable.fondo_mazamorra_quinua,
        6 to R.drawable.fondo_mate_coca
    )
    val fondoActual = fondosPorPlato[plato.id] ?: R.drawable.fondo_papa_huancaina

    Box(modifier = Modifier.fillMaxSize()) {

        // CAPA 1 — Fondo completo (ocupa toda la pantalla):
        Image(
            painter = painterResource(fondoActual),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // CAPA 2 — Contenido scrolleable encima del fondo:
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // — TopBar:
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable { onAtras() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color(0xFF2D4A2D))
                    Text(
                        " Atrás", style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2D4A2D)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder, null,
                        tint = Color(0xFFE57373),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            // — Espacio para que el plato de la imagen sea visible 
            //   (la foto está arriba a la derecha en el fondo):
            Spacer(Modifier.height(200.dp))

            // — Zona de contenido (sobre la zona crema del fondo):
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // — Emoji + Nombre:
                Row(verticalAlignment = Alignment.Top) {
                    Text(plato.emoji, fontSize = 32.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(
                        plato.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2D4A2D)
                    )
                }

                // — Chip categoría:
                AssistChip(
                    onClick = {},
                    label = { Text("Categoría: ${plato.categoria}") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Storefront, null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFFFFF3E0),
                        labelColor = Color(0xFF8B4513),
                        leadingIconContentColor = Color(0xFF8B4513)
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = Color(0xFFFFCC80)
                    )
                )

                // Indicador: ya tenías este plato agregado antes
                if (cantidadEnCarrito > 0) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2D4A2D),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Ya tienes $cantidadEnCarrito en tu pedido",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF2D4A2D),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // — Descripción:
                Text(
                    plato.descripcionCompleta,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp
                )

                // — Card Precio:
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                        Text(
                            "Precio", style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF8B4513)
                        )
                        Text(
                            "S/ ${"%.2f".format(plato.precio)}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF8B4513)
                        )
                    }
                }

                // — Card Cantidad + Subtotal:
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF5EDE3))
                                    .clickable { if (cantidad > 1) cantidad-- }
                            ) {
                                Icon(
                                    Icons.Default.Remove, null,
                                    modifier = Modifier.align(Alignment.Center),
                                    tint = Color(0xFF8B4513)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    cantidad.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Cantidad",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Box(
                                Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2D4A2D))
                                    .clickable { cantidad++ }
                            ) {
                                Icon(
                                    Icons.Default.Add, null,
                                    modifier = Modifier.align(Alignment.Center),
                                    tint = Color.White
                                )
                            }
                        }

                        HorizontalDivider(color = Color(0xFFEEEEEE))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF5EDE3))
                                ) {
                                    Icon(
                                        Icons.Default.LocalOffer, null,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(18.dp),
                                        tint = Color(0xFF8B4513)
                                    )
                                }
                                Spacer(Modifier.width(8.dp))
                                Text("Subtotal", style = MaterialTheme.typography.titleMedium)
                            }
                            Text(
                                "S/ ${"%.2f".format(plato.precio * cantidad)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFC0622B)
                            )
                        }
                    }
                }

                // — Card Nota:
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Edit, null,
                                tint = Color(0xFF8B4513),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Nota ", style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "(opcional)",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nota,
                            onValueChange = { if (it.length <= 80) nota = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Ejemplo: sin cebolla, término 3/4, sin picante...",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            maxLines = 3,
                            supportingText = {
                                Text(
                                    "${nota.length}/80",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }

                // — Botón Agregar al Pedido:
                Button(
                    onClick = {
                        pedidoViewModel.agregarAlPedido(plato, cantidad, nota)
                        onPlatoAgregado()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D4A2D)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.ShoppingBag, null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Agregar al Pedido",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // — Footer:
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Shield, null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "Ingredientes frescos y de calidad",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // — Espacio final para que las hojas decorativas del fondo 
                //   sean visibles:
                Spacer(Modifier.height(80.dp))
            }
        }

        // FAB del carrito (opcional, pero lo mantengo si hay items para no perder funcionalidad)
        if (cantidadEnPedido > 0) {
            FloatingActionButton(
                onClick = onVerCarrito,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color(0xFF2D4A2D),
                contentColor = Color.White
            ) {
                Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, null)
                    Spacer(Modifier.width(8.dp))
                    Text("$cantidadEnPedido · S/ ${"%.2f".format(totalPedido)}")
                }
            }
        }
    }
}
