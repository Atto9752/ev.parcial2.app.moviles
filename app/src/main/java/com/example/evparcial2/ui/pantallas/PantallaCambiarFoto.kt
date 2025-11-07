package com.example.evparcial2.ui.pantallas

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evparcial2.ui.components.images.ImagenInteligente
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evparcial2.viewmodels.ViewModelPerfil
import com.google.type.Date
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PantallaCambiarFoto(
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    val viewModelPerfil: ViewModelPerfil = viewModel()
    val imagenUri by viewModelPerfil.imagenUri.collectAsState()

    // galeria
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModelPerfil.setImage(uri)
    }

    // camara
    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) viewModelPerfil.setImage(cameraUri)
    }

    // Crear URI para la imagen
    fun createImageUri(context: Context): Uri {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(java.util.Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // Diseño
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Vista previa
        ImagenInteligente(imagenUri)

        Spacer(modifier = Modifier.height(32.dp))

        // Boton galeria
        Button(
            onClick = { pickImageLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar de Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Boton camara
        Button(
            onClick = {
                // Por ahora sin permisos para simplificar
                val uri = createImageUri(context)
                cameraUri = uri
                takePictureLauncher.launch(uri)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar Foto con Cámara")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Boton volver
        Button(onClick = onVolver) {
            Text("Volver al Perfil")
        }
    }
}