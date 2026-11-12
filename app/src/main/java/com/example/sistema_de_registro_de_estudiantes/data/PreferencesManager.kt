package com.example.sistema_de_registro_de_estudiantes.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("RegistroEstudiantes", Context.MODE_PRIVATE)

    fun saveRegistro(estudiante: Estudiante) {
        sharedPreferences.edit()
            .putString(KEY_MATRICULA, estudiante.matricula)
            .putString(KEY_NOMBRE, estudiante.nombre)
            .putString(KEY_CARRERA, estudiante.carrera)
            .putString(KEY_TURNO, estudiante.turno)
            .putBoolean(KEY_ACTIVO, estudiante.activo)
            .apply()
    }

    fun getRegistro(): Estudiante {
        return Estudiante(
            matricula = sharedPreferences.getString(KEY_MATRICULA, "") ?: "",
            nombre = sharedPreferences.getString(KEY_NOMBRE, "") ?: "",
            carrera = sharedPreferences.getString(KEY_CARRERA, "") ?: "",
            turno = sharedPreferences.getString(KEY_TURNO, "Matutino") ?: "Matutino",
            activo = sharedPreferences.getBoolean(KEY_ACTIVO, true)
        )
    }

    companion object {
        const val KEY_MATRICULA = "key_matricula"
        const val KEY_NOMBRE = "key_nombre"
        const val KEY_CARRERA = "key_carrera"
        const val KEY_TURNO = "key_turno"
        const val KEY_ACTIVO = "key_activo"
    }
}
