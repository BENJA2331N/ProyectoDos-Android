package com.example.milsaboresapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.milsaboresapp.model.UsuarioErrores
import com.example.milsaboresapp.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {
    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado

    fun onNombreChange(nuevoNombre: String) {
        _estado.update { it.copy(nombre = nuevoNombre, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(nuevoCorreo: String) {
        _estado.update { it.copy(correo = nuevoCorreo, errores = it.errores.copy(correo = null)) }
    }

    fun onContrasenaChange(nuevaContrasena: String) {
        _estado.update { it.copy(contrasena = nuevaContrasena, errores = it.errores.copy(contrasena = null)) }
    }

    fun onDireccionChange(nuevaDireccion: String) {
        _estado.update { it.copy(direccion = nuevaDireccion, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange(nuevoAceptarTerminos: Boolean) {
        _estado.update { it.copy(aceptaTerminos = nuevoAceptarTerminos) }
    }

    fun estaValidadoElFormulario(): Boolean {
        val formularioActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (formularioActual.nombre.isBlank()) "El campo es obligatorio" else null,
            correo = if (!Patterns.EMAIL_ADDRESS.matcher(formularioActual.correo).matches()) "El correo debe ser valido" else null,
            contrasena = if (formularioActual.contrasena.length < 6) "La contraseña debe tener al menos 6 caracteres" else null,
            direccion = if (formularioActual.direccion.isBlank()) "El campo es obligatorio" else null,
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.contrasena,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }
}