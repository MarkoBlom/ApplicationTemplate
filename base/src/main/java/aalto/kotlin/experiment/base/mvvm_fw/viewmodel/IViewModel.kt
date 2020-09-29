package aalto.kotlin.experiment.base.mvvm_fw.viewmodel

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle

/**
 * APIs implemented by a ViewModel, it receives the Activity (View) lifecycle notifications/callbacks.
 *
 * Method names reflect those of Activity's (View)
 * https://developer.android.com/guide/components/activities/activity-lifecycle
 *
 */
interface IViewModel {



    // Handle lifecycle callbacks from View here:
    fun onCreate()

    fun onResume()

    fun onStart()

    fun onPause()

    fun onStop()

    fun onDestroy() {}


    fun onActivityResult(requestCode: Int,
                         resultCode: Int,
                         data: Intent?) : Boolean


    fun onConfigurationChanged(newConfig: Configuration?)

    fun onSaveInstanceState(outState: Bundle?)

    fun onRestoreInstanceState(savedInstanceState: Bundle?)
}