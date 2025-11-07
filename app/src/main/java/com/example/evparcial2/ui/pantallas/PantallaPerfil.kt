package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evparcial2.data.model.Usuario
import com.example.evparcial2.ui.components.images.ImagenInteligente
import com.example.evparcial2.viewmodels.ViewModelPerfil
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil(
    usuario: Usuario,
    onVolver: () -> Unit,
    onCerrarSesion: () -> Unit,
    onEditarFoto: () -> Unit
) {

    val viewModelPerfil: ViewModelPerfil = viewModel()
    val imagenUri by viewModelPerfil.imagenUri.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        // Icono simple de flecha - NO requiere imports especiales
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Foto perfil
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .clickable { onEditarFoto() },
                    contentAlignment = Alignment.Center
                ) {
                    ImagenInteligente(imagenUri = imagenUri)
                }

                // editar foto
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .align(Alignment.BottomEnd)
                        .clickable { onEditarFoto() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar foto",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // infor user
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Información Personal",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoItemSimple("Nombre: ${usuario.nombre}")
                    InfoItemSimple("Email: ${usuario.email}")
                    InfoItemSimple("Rol: ${usuario.rol.replaceFirstChar { it.uppercase() }}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones varios
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilledTonalButton(
                    onClick = { /* Configuraciones */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Configuración")
                }

                FilledTonalButton(
                    onClick = { /* Ayuda */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ayuda y Soporte")
                }

                Button(
                    onClick = onCerrarSesion,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text("Cerrar Sesión")
                }
            }
        }
    }

}

@Composable
fun InfoItemSimple(texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(texto)
    }
}