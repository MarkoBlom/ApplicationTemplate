package aalto.kotlin.experiment.featureone

import aalto.kotlin.experiment.base.BaseApplication.Companion.baseComponent
import aalto.kotlin.experiment.base.location.LocationProvider
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.Action.Type.*
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import aalto.kotlin.experiment.featureone.base.MapActivity
import aalto.kotlin.experiment.featureone.dagger.DaggerFeatureOneComponent
import aalto.kotlin.experiment.featureone.dagger.FeatureOneModule
import aalto.kotlin.experiment.featureone.databinding.ActivityFeatureoneBinding
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject



class FeatureOneActivity: MapActivity() {

    private var mLastLocation: Location? = null

    @Inject
    override lateinit var mViewModel: IViewModel


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

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.pfj_locations_map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
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

            else -> {
                Log.d("=MB=", "  -> Unhandled action ???");
            }
        }
    }
}