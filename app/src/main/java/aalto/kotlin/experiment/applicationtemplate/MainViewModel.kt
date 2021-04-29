package aalto.kotlin.experiment.applicationtemplate

import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import aalto.kotlin.experiment.base.network.WebApi
import android.content.Context
import android.text.SpannableString
import android.util.Log
import android.view.View
import androidx.databinding.Bindable

/**
 *
 */
class MainViewModel(private val baseRepository: BaseRepository,
                    private val webApi: WebApi,
                    private val context: Context): BaseViewModel(){

    /**
     * Text to show in tab header
     */
    @Bindable
    var headerText = ""

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()

        Log.d("=MB=","MainViewModel::onCreate()")
    }

    /**
     *
     */
    override fun onDestroy() {
        Log.d("=MB=","MainViewModel::onDestroy()")

        super.onDestroy()
    }

    /**
     * Goto Feature One -btn clicked
     */
    fun onNextClicked(view : View) {
        Log.d(TAG,"MainViewModel::onNextClicked()")

        nextAction.value = Action.create(Action.Type.NEXT_SCREEN)
    }

    /**
     *
     */
    fun onValidPhoneNumberEntered() {
        nextAction.value = Action.create(Action.Type.VALID_PHONE_NUMBER_ENTERED)
    }

    /**
     * One of the header navigation buttons ('Messages','Talk','Profile','Options','Care Team') is clicked
     */
    fun onHeaderItemClicked(view : View) {
        Log.d(TAG,"MainViewModel::onHeaderItemClicked( to: ${view.tag} )")

        // Update navigation header text accordingly
        when(view.tag) {
            context.getString(R.string.messages) -> headerText = context.getString(R.string.messages)

            context.getString(R.string.talk) -> headerText = context.getString(R.string.talk)

            context. getString(R.string.profile) -> headerText = context.getString(R.string.profile)

            context.getString(R.string.options) -> headerText = context.getString(R.string.options)

            context.getString(R.string.care_team) -> headerText = context.getString(R.string.care_team)
        }
        notifyPropertyChanged(BR.headerText)

        // let the View handle the transition
        nextAction.value = Action.create(Action.Type.NEXT_TAB, mapOf("VIEW_TAG" to view.tag))
    }

    /**
     *
     */
    fun onBackButtonClicked(view: View) {
        Log.d(TAG,"MainViewModel::onBackButtonClicked( from: ${view.tag} )")

        nextAction.value = Action.create(Action.Type.BACK_FROM_TAB)
    }

    companion object {

    }
}