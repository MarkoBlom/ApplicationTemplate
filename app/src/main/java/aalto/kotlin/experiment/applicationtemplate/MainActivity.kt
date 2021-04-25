package aalto.kotlin.experiment.applicationtemplate

import aalto.kotlin.experiment.applicationtemplate.dagger.DaggerMainComponent
import aalto.kotlin.experiment.applicationtemplate.dagger.MainModule
import aalto.kotlin.experiment.applicationtemplate.databinding.ActivityMainWithoutBannerBinding
import aalto.kotlin.experiment.applicationtemplate.databinding.HomescreenMainHeaderBinding
import aalto.kotlin.experiment.applicationtemplate.databinding.HomescreenTabHeaderBinding
import aalto.kotlin.experiment.base.BaseApplication
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.ViewModelActivity
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import aalto.kotlin.experiment.featureone.FeatureOneActivity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main_without_banner.*
import kotlinx.android.synthetic.main.homescreen_main_header.*
import kotlinx.android.synthetic.main.homescreen_tab_header.*
import java.util.*
import javax.inject.Inject

const val TAG = "=MB="

class MainActivity : ViewModelActivity() {

    @Inject
    override lateinit var mViewModel : IViewModel

    // scene for header transition
    lateinit var sceneRootHeader: ViewGroup

    lateinit var fadeTransition: Transition

    /**
     * Kick off injection here : ViewModel
     */
    init {
        DaggerMainComponent.builder()
            .mainModule(MainModule())
            .baseComponent(BaseApplication.baseComponent)
            .build()
            .inject(this)
    }

    /**
     * https://www.tutorialspoint.com/android/android_datepicker_control.htm
     */
    private val myDateListener =
        DatePickerDialog.OnDateSetListener { arg0, year, month, day ->
            Log.d(
                "=MB=",
                "MainActivity.myDateListener::onDateSet( year: $year | month: ${month + 1} | day: $day   )"
            )

            var m = month+1
            val month2 = if( m < 10) {"0" + m} else m
            val day = if(day < 10) {"0" + day} else day

            val dob = month2.toString() + "/" + day + "/" + year
            onNewDate(dob)
        }


    private val mCalendar = Calendar.getInstance()

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView = layoutInflater.inflate(R.layout.activity_main_without_banner, null, false)
        val binding = DataBindingUtil.bind<ActivityMainWithoutBannerBinding>(rootView)

        setContentView(rootView)

        // bind viewModel
        binding?.viewModel = mViewModel as MainViewModel

        // bind DateOfBirth formatter to DoB edit
        binding?.phoneNumberEdit = phonenum_edit

        sceneRootHeader = header_scene_root

        fadeTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.fade_transition)


    }

    /**
     *
     */
    fun onShowDatePickerDialog(view: View) {

        showDialog(999)
    }

    /**
     *
     */
    override fun onCreateDialog(id: Int): Dialog {

        return when(id) {
            999 -> DatePickerDialog(
                this,
                myDateListener, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            )

            else -> super.onCreateDialog(id)
        }
    }

    /**
     *
     */
    private fun onNewDate(date: String) {dob_calendarview.text = date}


    /**
     * Handles LiveData callbacks from ViewModel
     */
    override fun onNextAction(action: Action) {
        Log.d(TAG, "MainActivity::onNextAction(${action.type.name})")

        when (action.type) {
            Action.Type.NEXT_SCREEN -> {

                if( phonenum_edit.text!!.length < 12 ) {
                    // show banner if transition has not occurred yet
                    if( main_motionLayout.progress == 0.0f ) {
                        main_motionLayout.transitionToEnd()
                    }
                } else {
                    startActivity(Intent(this, FeatureOneActivity::class.java))
                    //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
                }
            }

            Action.Type.VALID_PHONE_NUMBER_ENTERED -> {
                // dismiss banner if transition has occurred
                if( main_motionLayout.progress != 0.0f ) {
                    main_motionLayout.transitionToStart()
                }
            }

            Action.Type.NEXT_TAB -> transitionToNextScreen(action.data?.get("VIEW_TAG") as String)
            Action.Type.BACK_FROM_TAB -> handleNavigationBackFromTab()
            Action.Type.PROGRESS_ANIM_SHOW -> { /*etc.*/
            }
            else -> Log.d(TAG, "  -> Unhandled action")
        }
    }

    /**
     * Uses the tag (string) passed from ViewModel to determine where to navigate
     */
    private fun transitionToNextScreen(tag: String) {
        Log.d(TAG, "MainActivity::navigateToNextScreen( to: $tag )")

        // apply this because view cache is invalid after inflating new tab
        clearFindViewByIdCache()

        // show button clicked animation ( for both image & text)
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
            }

            getString(R.string.options) -> {
                options_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
                options.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            }

            getString(R.string.care_team) -> {
                care_team_img.startAnimation(
                    AnimationUtils.loadAnimation(
                        this,
                        R.anim.button_click
                    )
                )
                care_team.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))
            }
        }

        // transition
        val view = layoutInflater.inflate(R.layout.homescreen_tab_header, null, true)
        val binding = DataBindingUtil.bind<HomescreenTabHeaderBinding>(view)
        binding?.viewModel = mViewModel as MainViewModel

        val endScene = Scene(sceneRootHeader, view)

        Handler().postDelayed({
            TransitionManager.go(endScene, fadeTransition)
        }, 500)
    }

    /**
     * Back button is clicked from header
     */
    private fun handleNavigationBackFromTab(){

        // apply this because view cache is invalid after inflating home screen
        clearFindViewByIdCache()

        // animate back button click
        tab_back_btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click))

        // transition
        val view = layoutInflater.inflate(R.layout.homescreen_main_header, null, true)
        val binding = DataBindingUtil.bind<HomescreenMainHeaderBinding>(view)
        binding?.viewModel = mViewModel as MainViewModel

        val endScene = Scene(sceneRootHeader, view)

        Handler().postDelayed({
            TransitionManager.go(endScene, fadeTransition)
        }, 500)
    }
}