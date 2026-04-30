package com.tecsup.sabor_andino.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.sabor_andino.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    totalPedido : Double,
    onAtras     : () -> Unit,
    onConfirmar : (tipo: String, mesa: String) -> Unit
) {
    var tipoSeleccionado by remember { mutableStateOf("") } // "mesa" o "llevar"
    var numeroMesa       by remember { mutableStateOf("") }
    var error            by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de marca
        Image(
            painter = painterResource(R.drawable.fondo_pago),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Finalizar Pedido",
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "¿Cómo disfrutarás tu comida?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513),
                    textAlign = TextAlign.Center
                )
                
                Spacer(Modifier.height(8.dp))
                
                Text(
                    "Selecciona una opción para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OptionCard(
                        title = "En Mesa",
                        icon = Icons.Default.Dining,
                        isSelected = tipoSeleccionado == "mesa",
                        modifier = Modifier.weight(1f),
                        onClick = { 
                            tipoSeleccionado = "mesa"
                            error = ""
                        }
                    )
                    OptionCard(
                        title = "Para Llevar",
                        icon = Icons.Default.ShoppingBag,
                        isSelected = tipoSeleccionado == "llevar",
                        modifier = Modifier.weight(1f),
                        onClick = { 
                            tipoSeleccionado = "llevar"
                            numeroMesa = ""
                            error = ""
                        }
                    )
                }

                // Campo de número de mesa con animación simple (aparición)
                if (tipoSeleccionado == "mesa") {
                    Spacer(Modifier.height(32.dp))
                    OutlinedTextField(
                        value = numeroMesa,
                        onValueChange = { 
                            if (it.length <= 3) {
                                numeroMesa = it
                                error = ""
                            }
                        },
                        label = { Text("Número de Mesa") },
                        placeholder = { Text("Ej. 5") },
                        modifier = Modifier.fillMaxWidth(0.7f),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2D4A2D),
                            focusedLabelColor = Color(0xFF2D4A2D),
                            unfocusedBorderColor = Color(0xFFCCCCCC)
                        )
                    )
                }

                if (error.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        error, 
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.weight(1f))

                // Resumen de pago
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Total a pagar",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray
                            )
                            Text(
                                "S/ ${"%.2f".format(totalPedido)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFC0622B)
                            )
                        }
                        
                        Spacer(Modifier.height(24.dp))
                        
                        Button(
                            onClick = {
                                when {
                                    tipoSeleccionado.isEmpty() -> error = "Por favor, selecciona una opción"
                                    tipoSeleccionado == "mesa" && numeroMesa.isBlank() -> error = "Indícanos tu número de mesa"
                                    else -> onConfirmar(tipoSeleccionado, numeroMesa)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D4A2D)),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                "Confirmar Pedido",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptionCard(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF2D4A2D) else Color.White.copy(alpha = 0.8f)
    val contentColor = if (isSelected) Color.White else Color(0xFF2D4A2D)
    val borderColor = if (isSelected) Color(0xFF2D4A2D) else Color(0xFFCCCCCC).copy(alpha = 0.5f)

    Card(
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = contentColor
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}
