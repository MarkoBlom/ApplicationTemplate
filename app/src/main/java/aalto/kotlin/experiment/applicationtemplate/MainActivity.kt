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
import android.content.Intent
import androidx.databinding.DataBindingUtil
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.homescreen_main_header.*
import javax.inject.Inject

const val TAG = "=MB="

class MainActivity : ViewModelActivity() {

    @Inject
    override lateinit var mViewModel : IViewModel

    /**
     * Kick off injection here : ViewModel
     */
    init {
        DaggerMainComponent.builder()
            .mainModule( MainModule() )
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
        binding!!.viewModel = mViewModel as MainViewModel
    }

    /**
     * LiveData handler
     */
    override fun onNextAction(action: Action) {
        Log.d("=MB=","MainActivity::onNextAction(${action.type.name})")

        when (action.type) {
            Action.Type.NEXT_SCREEN -> {

                startActivity(Intent(this, FeatureOneActivity::class.java))
                //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }

            Action.Type.NEXT_TAB -> transitionToNextScreen( action.data?.get("VIEW_TAG") as String )

            Action.Type.PROGRESS_ANIM_SHOW -> { /*etc.*/ }

            else -> {
                Log.d("=MB=","  -> Unhandled action");
            }
        }
    }

    /**
     * Uses the tag (string) passed from ViewModel to determine where to navigate
     */
    private fun transitionToNextScreen(tag: String) {
        Log.d(TAG, "MainActivity::navigateToNextScreen( to: $tag )")

        when(tag) {
            getString(R.string.messages) -> {
                messages_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
                messages.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            }

            getString(R.string.talk) -> {
                talk_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
                talk.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            }

            getString(R.string.profile) -> {
                profile_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
                profile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))

//                val view = layoutInflater.inflate(R.layout.homescreen_profile_header, null, true)
//                val binding = DataBindingUtil.bind<HomescreenProfileHeaderBinding>(view)
//                binding?.viewModel = mViewModel as HomeScreenViewModel
//
//                endScene = Scene(sceneRootHeader, view)
            }

            getString(R.string.options) -> {
                options_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
                options.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            }

            getString(R.string.care_team) -> {
                care_team_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
                care_team.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            }
        }
//
//        val view = layoutInflater.inflate(R.layout.homescreen_profile_header, null, true)
//        val binding = DataBindingUtil.bind<HomescreenProfileHeaderBinding>(view)
//        binding?.viewModel = mViewModel as HomeScreenViewModel
//
//        endScene = Scene(sceneRootHeader, view)
//
//        Handler().postDelayed({
//            TransitionManager.go(endScene, fadeTransition)
//        }, 500)
    }
}