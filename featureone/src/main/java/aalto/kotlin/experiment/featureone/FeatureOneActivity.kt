package aalto.kotlin.experiment.featureone

import aalto.kotlin.experiment.base.BaseApplication
import aalto.kotlin.experiment.base.BaseApplication.Companion.baseComponent
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.featureone.dagger.DaggerFeatureOneComponent
import aalto.kotlin.experiment.featureone.dagger.FeatureOneModule
import aalto.kotlin.experiment.featureone.databinding.ActivityFeatureoneBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.elyeproj.base.mvvm_fw.viewmodel.IViewModel
import javax.inject.Inject


class FeatureOneActivity : ViewModelActivity() {

    @Inject
    override lateinit var mViewModel : IViewModel

    /**
     * Kick off injection here : ViewModel
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

        val rootView = layoutInflater.inflate(R.layout.activity_featureone, null, false)
        val binding = DataBindingUtil.bind<ActivityFeatureoneBinding>(rootView)

        setContentView(rootView)

        // bind viewModel
        binding!!.setViewModel(mViewModel as FeatureOneViewModel)
    }

    /**
     * Callback from ViewModel
     */
    override fun onViewModelEvent(action: Action) {
        Log.d("=MB=","FeatureOneActivity::onViewModelEvent() ${action.type.name}")

        when (action.type) {
            Action.Type.NEXT_SCREEN -> {
                val intent = Intent()
                intent.setClassName(this, "aalto.kotlin.experiment.featuretwo.FeatureTwoActivity")
                startActivity(intent)
                //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }

            Action.Type.PROGRESS_ANIM_SHOW -> { /*etc.*/ }

            else -> {
                Log.d("=MB=","  -> Unhandled action");
            }

        }

    }
}