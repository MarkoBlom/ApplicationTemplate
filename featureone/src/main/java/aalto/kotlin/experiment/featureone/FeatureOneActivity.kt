package aalto.kotlin.experiment.featureone

import aalto.kotlin.experiment.base.BaseApplication.Companion.baseComponent
import aalto.kotlin.experiment.base.location.LocationProvider
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.Action.Type.*
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import aalto.kotlin.experiment.featureone.dagger.DaggerFeatureOneComponent
import aalto.kotlin.experiment.featureone.dagger.FeatureOneModule
import aalto.kotlin.experiment.featureone.databinding.ActivityFeatureoneBinding
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class FeatureOneActivity: ViewModelActivity(), OnMapReadyCallback {

    private var mLastLocation: Location? = null

    @Inject
    override lateinit var mViewModel: IViewModel

    @Inject
    lateinit var mModel: BaseRepository

    var mGoogleMap: GoogleMap? = null

    var mIsMapAvailable = false

    /**
     * Kick off dependency injection here :
     * ViewModel and its' dependencies -> webapi, model(=data repository)
     */
    init {
        DaggerFeatureOneComponent.builder()
            .featureOneModule(FeatureOneModule())
            .baseComponent(baseComponent)
            .build()
            .inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding : ActivityFeatureoneBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_featureone)

        // bind viewModel
        binding.viewModel = mViewModel as FeatureOneViewModel

        LocationProvider().registerLifecycle(this.lifecycle,
            callback = { mLastLocation = it }
        )
    }

    override fun onDestroy() {
        mIsMapAvailable = false

        super.onDestroy()
    }

    /**
     * LiveData observer, callback from ViewModel
     */
    override fun onNextAction(action: Action) {
        Log.d("=MB=", "FeatureOneActivity::onNextAction(${action.type.name})")

        when (action.type) {
            NEXT_SCREEN -> {
                val intent = Intent()
                intent.setClassName(this, "aalto.kotlin.experiment.featuretwo.FeatureTwoActivity")
                startActivity(intent)
                //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            PROGRESS_ANIM_SHOW -> displayProgressAnimation()
            PROGRESS_ANIM_DISMISS -> hideProgressAnimation()
            PFJ_LOCATION_DATA_READY -> {
                Log.d("=MB=", "      PFJ location data is available...")
                if (mIsMapAvailable)
                    populateMap()
            }
            else -> {
                Log.d("=MB=", "  -> Unhandled action ???");
            }
        }
    }

    //region GOOGLE MAP

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     *
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(gMap: GoogleMap?) {

        mIsMapAvailable = true;
        mGoogleMap = gMap;

        if( mModel.pfjLocations.isNotEmpty() )
            populateMap()

     }

    private fun populateMap() {

        var location: LatLng

        // Add markers to the map:
        for( loc in mModel.pfjLocations) {

            location = LatLng(loc.long.toDouble(), loc.lat.toDouble())

            mGoogleMap?.addMarker(MarkerOptions().position(location)
                .title(loc.name))
        }

        // Use the Geographic Center of the Contiguous United States:
        //Lebanon, Kansas
        val center = LatLng(39.8097343, -98.5556199)
        // 15.0f means street level accuracy
        // 5f is continent level
        mGoogleMap?.moveCamera( CameraUpdateFactory.newLatLngZoom(center ,5.0f) )
    }

    //endregion
}