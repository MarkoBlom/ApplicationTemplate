package aalto.kotlin.experiment.featuretwo

import aalto.kotlin.experiment.base.BaseApplication
import android.os.Bundle
import android.util.Log
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.featuretwo.dagger.DaggerFeatureTwoComponent
import aalto.kotlin.experiment.featuretwo.dagger.FeatureTwoModule
import aalto.kotlin.experiment.featuretwo.databinding.ActivityFeaturetwoBinding
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.elyeproj.base.mvvm_fw.viewmodel.IViewModel
import javax.inject.Inject

class FeatureTwoActivity : ViewModelActivity() {

    @Inject
    override lateinit var mViewModel : IViewModel

    /**
     * Kick off injection here : ViewModel
     */
    init {
        DaggerFeatureTwoComponent.builder()
            .featureTwoModule( FeatureTwoModule(this) ) // IViewContract i.e. observer
            .baseComponent(BaseApplication.baseComponent)
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView = layoutInflater.inflate(R.layout.activity_featuretwo, null, false)
        val binding = DataBindingUtil.bind<ActivityFeaturetwoBinding>(rootView)

        setContentView(rootView)

        // bind viewModel
        binding!!.setViewModel(mViewModel as FeatureTwoViewModel)
    }

    /**
     * Handles Callbacks from ViewModel
     */
    override fun onViewModelEvent(action: Action) {
        Log.d("=MB=", "FeatureTwoActivity::onViewModelEvent() ${action.type.name}")

        when (action.type) {
            Action.Type.PROGRESS_ANIM_SHOW -> displayProgressAnimation()
            Action.Type.PROGRESS_ANIM_DISMISS -> hideProgressAnimation()
            else -> {
                Log.d("=MB=", "  -> Unhandled action ?");
            }
        }
    }
}