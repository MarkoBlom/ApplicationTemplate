package aalto.kotlin.experiment.featureone

import aalto.kotlin.experiment.base.BaseApplication.Companion.baseComponent
import aalto.kotlin.experiment.base.location.LocationProvider
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.Action.Type.*
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.featureone.dagger.DaggerFeatureOneComponent
import aalto.kotlin.experiment.featureone.dagger.FeatureOneModule
import aalto.kotlin.experiment.featureone.databinding.ActivityFeatureoneBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import android.location.Location
import javax.inject.Inject


class FeatureOneActivity : ViewModelActivity() {

    private var mLastLocation: Location? = null

    @Inject
    override lateinit var mViewModel : IViewModel


    /**
     * Kick off injection here : ViewModel and its' dependencies
     */
    init {
        DaggerFeatureOneComponent.builder()
            .featureOneModule( FeatureOneModule(this) ) // IViewContract i.e. observer
            .baseComponent( baseComponent )
            .build()
            .inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        LONG WAY:
//        val rootView = layoutInflater.inflate(R.layout.activity_featureone, null, false)
//        val binding = DataBindingUtil.bind<ActivityFeatureoneBinding>(rootView)
//        setContentView(rootView)

        var binding : ActivityFeatureoneBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_featureone)

        // bind viewModel
        binding.viewModel = mViewModel as FeatureOneViewModel

        LocationProvider().registerLifecycle(this.lifecycle,
                                             callback = {mLastLocation = it}
                                            )
    }

    /**
     * Callback from ViewModel
     */
    override fun onViewModelEvent(action: Action) {
        Log.d("=MB=","FeatureOneActivity::onViewModelEvent() ${action.type.name}")

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
                Log.d("=MB=","  -> Unhandled action ???");
            }
        }
    }
}