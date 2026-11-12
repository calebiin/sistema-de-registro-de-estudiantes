# Registro de estudiantes

Aplicación Android hecha con Jetpack Compose. Captura el registro de un estudiante, lo guarda en el dispositivo y muestra los datos en una pantalla de confirmación.

## Formulario

La pantalla inicial pide:

- Matrícula y nombre completo
- Carrera, en un menú desplegable
- Turno: Matutino o Vespertino
- Estatus: Activo o Inactivo

Las carreras disponibles son:

- Ingeniería Civil
- Ingeniería Geodésica
- Ingeniería de Software (en modalidad escolarizada)
- Ingeniería de Software (Modalidad Virtual)
- Ingeniería en Procesos Industriales
- Ingeniería en Nanotecnología y Energías Renovables

Al pulsar **Registrar**, la app guarda el registro y abre el detalle con la matrícula, el nombre, la carrera, el turno y el estatus.

## Persistencia

El último registro se guarda con `SharedPreferences`. Al volver a abrir la aplicación, el formulario recupera la matrícula, el nombre, la carrera, el turno y el estatus.

## Estructura

```text
app/src/main/java/com/example/sistema_de_registro_de_estudiantes/
├── MainActivity.kt
├── navigation/AppNav.kt
├── data/Estudiante.kt
├── data/PreferencesManager.kt
├── ui/registro/RegistroScreen.kt
├── ui/detalle/DetalleScreen.kt
└── ui/theme/Theme.kt
```
La versión mínima es Android 7.0 (API 24).
