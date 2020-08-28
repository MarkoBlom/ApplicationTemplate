package aalto.kotlin.experiment.base.location

import android.location.Location
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
//import android.Manifest
//import android.content.pm.PackageManager
//import androidx.core.content.ContextCompat
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.location.LocationServices

class LocationProvider : LifecycleObserver {

    private var lastLocation: Location? = null
        set(value) {
            field = value
            value?.let {
                mCallback?.invoke(it)
            }
        }

    // gsm related
    //private var locationCallback: LocationCallback? = null
    //private var fusedLocationClient: FusedLocationProviderClient? = null

    private var mCallback: ((Location) -> Unit)? = null

    /**
     * Attach to Activity lifecycle to observe activity state
     */
    fun registerLifecycle( lifecycle: Lifecycle,
                           callback: ((Location) -> Unit) ) {
        lifecycle.addObserver(this)
        mCallback = callback
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d("=MB=","LocationProvider::ON_START")
        /*
        if (ContextCompat.checkSelfPermission(
                USBCoreSDK.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(USBCoreSDK.getContext())
            fusedLocationClient?.lastLocation
                ?.addOnSuccessListener { location ->
                    lastLocation = location
                    if (location == null || location.accuracy > 100) {
                        locationCallback = object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                stopLocation()
                                if (locationResult != null &&
                                    locationResult.locations.isNotEmpty()
                                ) {
                                    val newLocation = locationResult.locations[0]
                                    lastLocation = newLocation
                                }
                            }
                        }
                        fusedLocationClient?.requestLocationUpdates(
                            LocationRequest.create(),
                            locationCallback, null
                        )
                    }
                }
        }*/
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        // disconnect if connected
        Log.d("=MB=","LocationProvider::ON_STOP")
        stopLocation()
    }

    private fun stopLocation() {
        /*
        locationCallback?.let {
            fusedLocationClient?.removeLocationUpdates(locationCallback)
        }
        locationCallback = null
        fusedLocationClient = null
        */
    }

}