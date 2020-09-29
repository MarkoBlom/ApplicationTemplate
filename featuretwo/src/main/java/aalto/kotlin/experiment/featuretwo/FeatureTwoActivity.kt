package aalto.kotlin.experiment.featuretwo

import aalto.kotlin.experiment.base.BaseApplication
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.Action.*
import aalto.kotlin.experiment.base.mvvm_fw.Action.Type.*
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import aalto.kotlin.experiment.featuretwo.dagger.DaggerFeatureTwoComponent
import aalto.kotlin.experiment.featuretwo.dagger.FeatureTwoModule
import aalto.kotlin.experiment.featuretwo.databinding.ActivityFeaturetwoBinding
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil

import javax.inject.Inject

class FeatureTwoActivity : ViewModelActivity() {

    @Inject
    override lateinit var mViewModel : IViewModel

    /**
     * Kick off injection here : ViewModel
     */
    init {
        DaggerFeatureTwoComponent.builder()
            .featureTwoModule( FeatureTwoModule() )
            .baseComponent(BaseApplication.baseComponent)
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding : ActivityFeaturetwoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_featuretwo)

        // bind viewModel
        binding.viewModel = mViewModel as FeatureTwoViewModel
    }

    /**
     * Handles Callbacks from ViewModel
     */
    override fun onNextAction(action: Action) {
        Log.d("=MB=", "FeatureTwoActivity::onNextAction(${action.type.name})")

        when (action.type) {
            PROGRESS_ANIM_SHOW -> displayProgressAnimation()
            PROGRESS_ANIM_DISMISS -> hideProgressAnimation()
            else -> {
                Log.d("=MB=", "  -> Unhandled action ?");
            }
        }
    }
}