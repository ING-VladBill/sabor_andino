package com.tecsup.sabor_andino.navigation


import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tecsup.sabor_andino.screens.DetalleScreen
import com.tecsup.sabor_andino.screens.HomeScreen
import com.tecsup.sabor_andino.screens.LoginScreen
import com.tecsup.sabor_andino.screens.MenuScreen
import com.tecsup.sabor_andino.screens.PedidoScreen
import com.tecsup.sabor_andino.screens.PerfilScreen
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

object Rutas {
    const val LOGIN   = "login"
    const val HOME    = "home/{correo}"
    const val MENU    = "menu"
    const val DETALLE = "detalle/{platoId}"
    const val PEDIDO  = "pedido"
    const val PERFIL  = "perfil"

    fun homeConCorreo(correo: String) = "home/$correo"
    fun detalleConId(id: Int)         = "detalle/$id"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: PedidoViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN ,
        modifier = Modifier.safeDrawingPadding()
    ) {

        composable(Rutas.LOGIN) {
            LoginScreen(
                onLoginExitoso = { correo ->
                    navController.navigate(Rutas.homeConCorreo(correo)) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rutas.HOME,
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            HomeScreen(
                correo = correo,
                onVerMenu   = { navController.navigate(Rutas.MENU) },
                onVerPedido = { navController.navigate(Rutas.PEDIDO) },
                onVerPerfil = { navController.navigate(Rutas.PERFIL) }
            )
        }

        composable(Rutas.MENU) {
            MenuScreen(
                onPlatoClick = { id -> navController.navigate(Rutas.detalleConId(id)) },
                onAtras      = { navController.popBackStack() }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("platoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val platoId = backStackEntry.arguments?.getInt("platoId") ?: 0
            DetalleScreen(
                platoId         = platoId,
                pedidoViewModel = viewModel,
                onAtras = { navController.popBackStack() },
                onIrPerfil = { navController.navigate(Rutas.PEDIDO) }
            )
        }

        composable(Rutas.PEDIDO) {
            PedidoScreen(
                pedidoViewModel = viewModel,
                onAtras         = { navController.popBackStack() }
            )
        }

        composable(Rutas.PERFIL) {
            PerfilScreen(
                onAtras = { navController.popBackStack() }
            )
        }
    }
}