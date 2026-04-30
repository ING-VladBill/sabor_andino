package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.sabor_andino.R
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoScreen(
    pedidoViewModel : PedidoViewModel,
    onAtras         : () -> Unit,
    onIrACheckout   : () -> Unit,
    onVerMenu       : () -> Unit
) {
    val itemsPedido by pedidoViewModel.pedido.collectAsState()
    val totalPedido = itemsPedido.sumOf { it.plato.precio * it.cantidad }

    Box(modifier = Modifier.fillMaxSize()) {

        // CAPA 1 — Fondo:
        Image(
            painter = painterResource(R.drawable.fondo_home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // CAPA 2 — Scaffold transparente con bottomBar fijo:
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Mi Carrito",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2D4A2D)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onAtras) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color(0xFF2D4A2D))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            bottomBar = {
                // — Barra inferior fija con total + botón pagar:
                if (itemsPedido.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Total:",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "S/ ${"%.2f".format(totalPedido)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFC0622B)
                            )
                        }
                        Button(
                            onClick = onIrACheckout,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2D4A2D)
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                "Ir a pagar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->

            // — Contenido principal:
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // — ESTADO VACÍO (mostrar si itemsPedido.isEmpty()):
                if (itemsPedido.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.9f)
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp, horizontal = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp),
                                    tint = Color(0xFFCCCCCC)
                                )
                                Text(
                                    "Tu carrito está vacío",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Agrega platos del menú para comenzar",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = onVerMenu,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF2D4A2D)
                                    ),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        "Ver menú",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // — LISTA DE ÍTEMS (mostrar si itemsPedido.isNotEmpty()):
                items(itemsPedido) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // — Emoji del plato:
                            Box(
                                Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFF5EDE3)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(item.plato.emoji, fontSize = 28.sp)
                            }

                            // — Nombre, nota y controles:
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 10.dp)
                            ) {
                                Text(
                                    item.plato.nombre,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                if (item.nota.isNotEmpty()) {
                                    Text(
                                        "📝 ${item.nota}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                                // — Controles cantidad:
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFF5EDE3))
                                            .clickable { pedidoViewModel.modificarCantidad(item.plato.id, -1) }
                                    ) {
                                        Icon(
                                            Icons.Default.Remove, null,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .size(14.dp),
                                            tint = Color(0xFF8B4513)
                                        )
                                    }
                                    Text(
                                        item.cantidad.toString(),
                                        modifier = Modifier.padding(horizontal = 12.dp),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Box(
                                        Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF2D4A2D))
                                            .clickable { pedidoViewModel.modificarCantidad(item.plato.id, 1) }
                                    ) {
                                        Icon(
                                            Icons.Default.Add, null,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .size(14.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                            // — Subtotal + eliminar:
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(onClick = { pedidoViewModel.eliminarDelPedido(item.plato.id) }) {
                                    Icon(
                                        Icons.Default.Close, null,
                                        tint = Color(0xFFCCCCCC),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    "S/ ${"%.2f".format(item.plato.precio * item.cantidad)}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC0622B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
