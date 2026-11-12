package com.example.sistema_de_registro_de_estudiantes.data

data class Estudiante(
    val matricula: String,
    val nombre: String,
    val carrera: String,
    val turno: String,
    val activo: Boolean
) {
    val estatus: String
        get() = if (activo) "Activo" else "Inactivo"
}
