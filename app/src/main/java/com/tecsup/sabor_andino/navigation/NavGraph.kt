package com.tecsup.sabor_andino.navigation


import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tecsup.sabor_andino.screens.HomeScreen
import com.tecsup.sabor_andino.screens.LoginScreen
import com.tecsup.sabor_andino.screens.MenuScreen
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

object Rutas {
    const val LOGIN  = "login"
    const val HOME   = "home/{correo}"
    const val MENU   = "menu"
    const val DETALLE = "detalle/{platoId}"
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
                onVerPerfil = { navController.navigate(Rutas.PERFIL) }
            )
        }

        composable(Rutas.MENU) {
            MenuScreen(
                onPlatoClick = { id -> navController.navigate(Rutas.detalleConId(id)) },
                onAtras      = { navController.popBackStack() }
            )
        }



        // ⚠️ STUB — tu compañero reemplaza el Text() por DetalleScreen(...)
        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("platoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val platoId = backStackEntry.arguments?.getInt("platoId") ?: 0
            Text("Detalle del plato #$platoId — en construcción 🚧")
        }

        // ⚠️ STUB — tu compañero reemplaza el Text() por PerfilScreen(...)
        composable(Rutas.PERFIL) {
            Text("Perfil / Mi Pedido — en construcción 🚧")
        }
    }
}