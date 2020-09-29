package aalto.kotlin.experiment.base.mvvm_fw.view

import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import androidx.lifecycle.Observer

/**
 * Parent class for any Activity.
 *
 * Main purpose of this parent class is to notify the attached ViewModel
 * of the View's lifecycle and it'll react accordingly.
 * https://developer.android.com/guide/components/activities/activity-lifecycle
 *
 */
abstract class ViewModelActivity : AppCompatActivity(), IViewContract {

    private lateinit var mProgressDialog : ProgressDialog

    open lateinit var mViewModel : IViewModel

    // Create the observer which notifies the View with an Action object
    val actionObserver = Observer<Action> { newAction ->
        onNextAction(newAction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        (mViewModel as BaseViewModel).nextAction.observe(this, actionObserver)

        mViewModel.onCreate()
    }

    override fun onResume() {
        super.onResume()

        mViewModel.onResume()
    }

    override fun onStart() {
        super.onStart()

        mViewModel.onStart()
    }

    override fun onPause() {

        mViewModel.onPause()

        super.onPause()
    }

    override fun onStop() {
        super.onStop()

        mViewModel.onStop()
    }

    override fun onDestroy() {

        mViewModel.onDestroy()

        super.onDestroy()
    }

    /**
     * By default VM doesn't handle this and returns false so that base class can handle it safely
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if( !mViewModel.onActivityResult(requestCode, resultCode, data) )
            super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * To declare that your activity handles a configuration change, not the system without
     * recreating the View edit the appropriate <activity> element in your manifest file to
     * include the android:configChanges attribute with a value that represents
     * the configuration you want to handle.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        mViewModel.onConfigurationChanged(newConfig)
    }

    /**
     * Invoked when the activity may be temporarily destroyed, let ViewModel save state and
     * consequently restore it as well either in OnCreate() or onRestoreInstanceState() after onStart()
     */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d("=MB=","ViewModelActivity::onSaveInstanceState()")

        mViewModel.onSaveInstanceState(outState)

        // call superclass to save any view hierarchy

        super.onSaveInstanceState(outState, outPersistentState)
    }

    /**
     * This callback is called only when there is a saved instance that is previously saved by using
     * onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
     * other state here, possibly usable after onStart() has completed.
     * The savedInstanceState Bundle is same as the one used in onCreate().
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        mViewModel.onRestoreInstanceState(savedInstanceState)

        super.onRestoreInstanceState(savedInstanceState)
    }


    // NOTE YOU SHOULD PLACE BELOW METHODS TO EXTENSION FUNCTION FILE e.g. extension/CompatActivityExtension.kt

    /**
     * Progress animation : SHOW
     */
    protected fun displayProgressAnimation() {

        // start progress Dialog animation:
        mProgressDialog = ProgressDialog.show(
            this,
            null,
            "LOADING...",
            false
        )
    }

    /**
     * Progress animation : HIDE
     */
    protected fun hideProgressAnimation() {

        mProgressDialog?.let {
            if( it.isShowing )
                mProgressDialog.dismiss()
        }
    }

    override fun onNextAction( action : Action) { /* stub */}

}