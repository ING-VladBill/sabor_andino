package com.tecsup.sabor_andino.navigation

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tecsup.sabor_andino.screens.CheckoutScreen
import com.tecsup.sabor_andino.screens.ConfirmacionScreen
import com.tecsup.sabor_andino.screens.DetalleScreen
import com.tecsup.sabor_andino.screens.HomeScreen
import com.tecsup.sabor_andino.screens.LoginScreen
import com.tecsup.sabor_andino.screens.MenuScreen
import com.tecsup.sabor_andino.screens.PedidoScreen
import com.tecsup.sabor_andino.screens.PerfilScreen
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

object Rutas {
    const val LOGIN        = "login"
    const val HOME         = "home/{correo}"
    const val MENU         = "menu"
    const val DETALLE      = "detalle/{platoId}"
    const val PEDIDO       = "pedido"
    const val CHECKOUT     = "checkout"
    const val CONFIRMACION = "confirmacion"
    const val PERFIL       = "perfil"

    fun homeConCorreo(correo: String) = "home/$correo"
    fun detalleConId(id: Int)         = "detalle/$id"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: PedidoViewModel
) {
    val pedidos by viewModel.pedido.collectAsState()
    // Total reactivo derivado de la lista observada (se actualiza solo con recomposición)
    val totalPedido = pedidos.sumOf { it.plato.precio * it.cantidad }

    NavHost(
        navController    = navController,
        startDestination = Rutas.LOGIN,
        modifier         = Modifier.safeDrawingPadding()
    ) {

        // ── LOGIN ────────────────────────────────────────────────────────────
        composable(Rutas.LOGIN) {
            LoginScreen(
                onLoginExitoso = { correo ->
                    viewModel.setCorreo(correo)
                    navController.navigate(Rutas.homeConCorreo(correo)) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── HOME ─────────────────────────────────────────────────────────────
        composable(
            route     = Rutas.HOME,
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            HomeScreen(
                correo           = correo,
                cantidadEnPedido = pedidos.size,
                onVerMenu        = { navController.navigate(Rutas.MENU) },
                onVerPedido      = { navController.navigate(Rutas.PEDIDO) },
                onVerPerfil      = { navController.navigate(Rutas.PERFIL) }
            )
        }

        // ── MENÚ ─────────────────────────────────────────────────────────────
        composable(Rutas.MENU) {
            MenuScreen(
                onPlatoClick     = { id -> navController.navigate(Rutas.detalleConId(id)) },
                onAtras          = { navController.popBackStack() },
                onVerCarrito     = { navController.navigate(Rutas.PEDIDO) },
                cantidadEnPedido = pedidos.size,
                totalPedido      = totalPedido,
                // Lambda: cuántos de cada plato hay en el carrito (para el badge por ítem)
                cantidadDeItem   = { platoId -> viewModel.cantidadDeItem(platoId) },
                onAgregarAlCarrito = { plato -> viewModel.agregarAlPedido(plato, 1, "") }
            )
        }

        // ── DETALLE ───────────────────────────────────────────────────────────
        composable(
            route     = Rutas.DETALLE,
            arguments = listOf(navArgument("platoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val platoId = backStackEntry.arguments?.getInt("platoId") ?: 0
            DetalleScreen(
                platoId          = platoId,
                pedidoViewModel  = viewModel,
                onAtras          = { navController.popBackStack() },
                onPlatoAgregado  = { navController.popBackStack() },   // vuelve al menú
                onVerCarrito     = { navController.navigate(Rutas.PEDIDO) },
                cantidadEnPedido = pedidos.size,
                totalPedido      = totalPedido,
                // Cuántos de ESTE plato específico ya están en el carrito
                cantidadEnCarrito = viewModel.cantidadDeItem(platoId)
            )
        }

        // ── CARRITO / PEDIDO ──────────────────────────────────────────────────
        composable(Rutas.PEDIDO) {
            PedidoScreen(
                pedidoViewModel = viewModel,
                onAtras         = { navController.popBackStack() },
                onIrACheckout   = { navController.navigate(Rutas.CHECKOUT) },
                onVerMenu       = { navController.navigate(Rutas.MENU) }
            )
        }

        // ── CHECKOUT ──────────────────────────────────────────────────────────
        composable(Rutas.CHECKOUT) {
            CheckoutScreen(
                totalPedido  = totalPedido,
                onAtras      = { navController.popBackStack() },
                onConfirmar  = { tipo, mesa ->
                    viewModel.confirmarPedido(tipo, mesa)   // guarda datos + limpia carrito
                    navController.navigate(Rutas.CONFIRMACION) {
                        // No puede volver al checkout ni al carrito vacío
                        popUpTo(Rutas.PEDIDO) { inclusive = true }
                    }
                }
            )
        }

        // ── CONFIRMACIÓN ──────────────────────────────────────────────────────
        composable(Rutas.CONFIRMACION) {
            val numeroPedido by viewModel.numeroPedido.collectAsState()
            val tipoEntrega  by viewModel.tipoEntrega.collectAsState()
            val numeroMesa   by viewModel.numeroMesa.collectAsState()
            ConfirmacionScreen(
                numeroPedido     = numeroPedido,
                tipoEntrega      = tipoEntrega,
                numeroMesa       = numeroMesa,
                onVolverAlInicio = {
                    navController.navigate(Rutas.homeConCorreo(viewModel.correo.value)) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── PERFIL ────────────────────────────────────────────────────────────
        composable(Rutas.PERFIL) {
            PerfilScreen(
                pedidoViewModel = viewModel,
                onAtras         = { navController.popBackStack() },
                onCerrarSesion  = {
                    viewModel.cerrarSesion()
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
