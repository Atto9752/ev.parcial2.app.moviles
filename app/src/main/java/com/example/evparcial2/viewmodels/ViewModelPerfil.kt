package com.example.evparcial2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.evparcial2.data.local.repository.RepoUsuarios
import kotlinx.coroutines.flow.MutableStateFlow
import android.net.Uri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelPerfil (private val repositorio: RepoUsuarios = RepoUsuarios()): ViewModel() {
    private val _imagenUri = MutableStateFlow<Uri?>(repositorio.getProfile().imagenUri)
    val imagenUri: StateFlow<Uri?> = _imagenUri


    fun setImage(uri: Uri?) {
        viewModelScope.launch {
            _imagenUri.value = uri
            repositorio.updateImage(uri)
        }
    }

}