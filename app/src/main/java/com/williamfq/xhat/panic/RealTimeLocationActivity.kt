package com.williamfq.xhat.panic

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.williamfq.xhat.databinding.ActivityRealTimeLocationBinding
import com.williamfq.domain.location.LocationTracker
import com.williamfq.xhat.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RealTimeLocationActivity : AppCompatActivity() {

    // Inyectamos el LocationTracker utilizando Dagger Hilt
    @Inject
    lateinit var locationTracker: LocationTracker

    private lateinit var binding: ActivityRealTimeLocationBinding
    private lateinit var mapFragment: com.google.android.gms.maps.SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealTimeLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificamos permisos de ubicación
        checkPermissions()

        // Configuramos el mapa de Google
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as com.google.android.gms.maps.SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return@getMapAsync
            }
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isZoomControlsEnabled = true
        }

        // Usamos el LocationTracker para obtener la ubicación en tiempo real
        lifecycleScope.launch {
            locationTracker.getCurrentLocation().collectLatest { location ->
                handleLocation(location)
            }
        }
    }

    private fun handleLocation(location: Location?) {
        if (location != null) {
            // Mostrar la ubicación en el TextView
            binding.tvLocation.text = "Lat: ${location.latitude}, Long: ${location.longitude}"
        } else {
            // En caso de error, mostrar un mensaje en el TextView
            binding.tvLocation.text = "No se pudo obtener la ubicación"
        }
    }

    private fun checkPermissions() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
