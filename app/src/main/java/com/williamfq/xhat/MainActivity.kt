package com.williamfq.xhat

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.williamfq.xhat.ui.theme.XhatTheme

class MainActivity : ComponentActivity() {

    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            Toast.makeText(this, "Todos los permisos fueron concedidos.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Algunos permisos fueron denegados. La aplicación puede no funcionar correctamente.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar permisos
        if (!arePermissionsGranted()) {
            requestPermissionsLauncher.launch(requiredPermissions)
        }

        setContent {
            XhatTheme {
                MainScreen()
            }
        }
    }

    // Verificar si todos los permisos están concedidos
    private fun arePermissionsGranted(): Boolean {
        return requiredPermissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
    }
}

@Composable
fun MainScreen() {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Permisos necesarios") },
            text = {
                Text(
                    text = "La aplicación requiere permisos para acceder a la cámara, micrófono, ubicación y almacenamiento. Sin estos permisos, algunas funciones no estarán disponibles."
                )
            },
            confirmButton = {
                Button(onClick = {
                    Toast.makeText(
                        context,
                        "Por favor, acepta los permisos en la configuración.",
                        Toast.LENGTH_SHORT
                    ).show()
                    showDialog = false
                }) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    Button(onClick = { showDialog = true }) {
        Text(text = "Mostrar Mensaje de Permisos")
    }
}
