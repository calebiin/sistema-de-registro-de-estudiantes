package com.example.sistema_de_registro_de_estudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sistema_de_registro_de_estudiantes.navigation.AppNav
import com.example.sistema_de_registro_de_estudiantes.ui.theme.RegistroEstudiantesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroEstudiantesTheme {
                AppNav()
            }
        }
    }
}
