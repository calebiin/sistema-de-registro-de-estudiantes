package com.example.sistema_de_registro_de_estudiantes.ui.registro

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sistema_de_registro_de_estudiantes.data.Estudiante
import com.example.sistema_de_registro_de_estudiantes.data.PreferencesManager

private val carreras = listOf(
    "Ingeniería Civil",
    "Ingeniería Geodésica",
    "Ingeniería de Software",
    "Ingeniería de Software (Modalidad Virtual)",
    "Ingeniería en Procesos Industriales",
    "Ingeniería en Nanotecnología y Energías Renovables"
)

private val turnos = listOf("Matutino", "Vespertino")

@Composable
fun RegistroScreen(onConfirm: (Estudiante) -> Unit) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }
    var turno by remember { mutableStateOf("Matutino") }
    var activo by remember { mutableStateOf(true) }
    var menuAbierto by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val registro = preferencesManager.getRegistro()
        matricula = registro.matricula
        nombre = registro.nombre
        carrera = registro.carrera
        turno = registro.turno.takeIf { it in turnos } ?: "Matutino"
        activo = registro.activo
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Registro de estudiantes",
                style = MaterialTheme.typography.headlineSmall
            )

            HorizontalDivider()

            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = carrera,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Carrera") },
                    placeholder = { Text("Seleccionar carrera") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Mostrar carreras"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { menuAbierto = true }
                )
                DropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = { menuAbierto = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    carreras.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                carrera = opcion
                                menuAbierto = false
                            }
                        )
                    }
                }
            }

            Text(text = "Turno", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                turnos.forEach { opcion ->
                    RadioButton(
                        selected = turno == opcion,
                        onClick = { turno = opcion }
                    )
                    Text(
                        text = opcion,
                        modifier = Modifier.clickable { turno = opcion }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Estatus", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (activo) "Activo" else "Inactivo",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = activo,
                    onCheckedChange = { activo = it }
                )
            }

            Button(
                onClick = {
                    val matriculaLimpia = matricula.trim()
                    val nombreLimpio = nombre.trim()
                    when {
                        matriculaLimpia.isBlank() || nombreLimpio.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Ingresa la matrícula y el nombre completo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        carrera.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Selecciona una carrera",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            val estudiante = Estudiante(
                                matricula = matriculaLimpia,
                                nombre = nombreLimpio,
                                carrera = carrera,
                                turno = turno,
                                activo = activo
                            )
                            preferencesManager.saveRegistro(estudiante)
                            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show()
                            onConfirm(estudiante)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }
    }
}
