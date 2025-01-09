package com.williamfq.xhat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    // Duración del splash en milisegundos (por ejemplo, 2 segundos)
    private val SPLASH_DISPLAY_LENGTH: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Asegúrate de tener este layout en res/layout

        // Handler para retrasar la transición a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish() // Cerrar SplashActivity para que no quede en la pila de actividades
        }, SPLASH_DISPLAY_LENGTH)
    }
}
