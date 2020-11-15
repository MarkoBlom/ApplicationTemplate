package aalto.kotlin.experiment.featureone.base

import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

/**
 * View contract for managing GoogleMap callback in VieModel itself
 */
open class MapActivity: ViewModelActivity(), OnMapReadyCallback {

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     *
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        // ViewModel handles this call back
        mViewModel.onMapReady(googleMap)
    }
}