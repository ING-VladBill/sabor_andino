package com.tecsup.sabor_andino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tecsup.sabor_andino.navigation.NavGraph
import com.tecsup.sabor_andino.ui.theme.Sabor_andinoTheme
import com.tecsup.sabor_andino.viewmodel.PedidoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sabor_andinoTheme() {
                val navController = rememberNavController()
                val pedidoViewModel: PedidoViewModel = viewModel()
                NavGraph(navController = navController, viewModel = pedidoViewModel)
            }
        }
    }
}