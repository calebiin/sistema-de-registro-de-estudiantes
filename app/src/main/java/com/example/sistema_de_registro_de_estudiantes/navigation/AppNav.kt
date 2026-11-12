package com.example.sistema_de_registro_de_estudiantes.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sistema_de_registro_de_estudiantes.data.Estudiante
import com.example.sistema_de_registro_de_estudiantes.ui.detalle.DetalleScreen
import com.example.sistema_de_registro_de_estudiantes.ui.registro.RegistroScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable("registro") {
            RegistroScreen(
                onConfirm = { estudiante ->
                    navController.navigate(rutaDetalle(estudiante))
                }
            )
        }

        composable(
            route = "detalle/{matricula}/{nombre}/{carrera}/{turno}/{estatus}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("estatus") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            DetalleScreen(
                matricula = args?.getString("matricula").orEmpty(),
                nombre = args?.getString("nombre").orEmpty(),
                carrera = args?.getString("carrera").orEmpty(),
                turno = args?.getString("turno").orEmpty(),
                estatus = args?.getString("estatus").orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun rutaDetalle(estudiante: Estudiante): String {
    val datos = listOf(
        estudiante.matricula,
        estudiante.nombre,
        estudiante.carrera,
        estudiante.turno,
        estudiante.estatus
    )
    return "detalle/" + datos.joinToString("/") { Uri.encode(it) }
}
