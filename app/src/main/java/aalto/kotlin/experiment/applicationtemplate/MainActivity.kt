package aalto.kotlin.experiment.applicationtemplate

import aalto.kotlin.experiment.applicationtemplate.dagger.DaggerMainComponent
import aalto.kotlin.experiment.applicationtemplate.dagger.MainModule
import aalto.kotlin.experiment.applicationtemplate.databinding.ActivityMainBinding
import aalto.kotlin.experiment.base.BaseApplication
import android.os.Bundle
import android.util.Log
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.featureone.FeatureOneActivity
import aalto.kotlin.experiment.featureone.dagger.DaggerFeatureOneComponent
import aalto.kotlin.experiment.featureone.dagger.FeatureOneModule
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.elyeproj.base.mvvm_fw.viewmodel.IViewModel
import javax.inject.Inject

class MainActivity : ViewModelActivity() {

    @Inject
    override lateinit var mViewModel : IViewModel

    /**
     * Kick off injection here : ViewModel
     */
    init {
        DaggerMainComponent.builder()
            .mainModule( MainModule(this) ) // IViewContract i.e. observer
            .baseComponent(BaseApplication.baseComponent)
            .build()
            .inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView = layoutInflater.inflate(R.layout.activity_main, null, false)
        val binding = DataBindingUtil.bind<ActivityMainBinding>(rootView)

        setContentView(rootView)

        // bind viewModel
        binding!!.setViewModel(mViewModel as MainViewModel)
    }

    /**
     * Callback from ViewModel
     */
    override fun onViewModelEvent(action: Action) {
        Log.d("=MB=","MainActivity::onViewModelEvent() ${action.type.name}")

        when (action.type) {
            Action.Type.NEXT_SCREEN -> {
                startActivity(Intent(this, FeatureOneActivity::class.java))
                //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }

            Action.Type.PROGRESS_ANIM_SHOW -> { /*etc.*/ }

            else -> {
                Log.d("=MB=","  -> Unhandled action");
            }
        }
    }
}