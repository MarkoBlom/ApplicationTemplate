package aalto.kotlin.experiment.base.mvvm_fw.viewmodel

import aalto.kotlin.experiment.base.mvvm_fw.Action
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.CheckResult
import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext



/**
 * Base class for used ViewModels, it receives the Activity (View) lifecycle notifications/callbacks.
 *
 * Method names reflect those of Activity to make it easy to understand.
 * https://developer.android.com/guide/components/activities/activity-lifecycle
 *
 */
open class BaseViewModel : BaseObservable(),
                            IViewModel,
                            CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main // + mJob

    protected lateinit var mJob : Job

    // Create a LiveData with type of Action
    val nextAction: MutableLiveData<Action> by lazy {
        MutableLiveData<Action>()
    }

    // take care of your disposables
    protected val mDisposables = CompositeDisposable()

    // View is the observer, use weak reference
    //var mObserver = WeakReference<IViewContract>(observer)

    // Handle lifecycle callbacks from View here:
    override fun onCreate() {
        mJob = Job()
    }

    override fun onResume() {
        // stub:
        // By default ViewModel's onResume -notification does nothing
    }

    override fun onStart() {
        // stub:
        // By default ViewModel's onStart -notification does nothing
    }

    override fun onPause() {
        // stub:
        // By default ViewModel's onPause -notification does nothing
    }

    override fun onStop() {
        // stub:
        // By default ViewModel's onStop -notification does nothing
    }

    override fun onDestroy() {

        // Cancel any pending Coroutines
        //if( mJob.isActive()) {mJob.cancel()}


        // By default ViewModel's onCreate -notification does nothing else
        //mObserver.clear()

        mDisposables.clear()
    }

    @CheckResult
    override fun onActivityResult(  requestCode: Int,
                                    resultCode: Int,
                                    data: Intent?) : Boolean {
        // By default returns false so that child can process the response
        // accordingly on need basis
        return false
    }

    /**
     * IMPORTANT: Make sure that developer understand the difference between the two methods below
     */
    override fun onConfigurationChanged(newConfig: Configuration?) {
        // stub:
        // By default ViewModel's onConfigurationChanged -notification does nothing
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        // stub:
        // By default ViewModel's onSaveInstanceState -callback does nothing
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        // stub:
        // By default ViewModel's onRestoreInstanceState -callback does nothing
    }

    override fun onMapReady(map: Any?) {
        //stub
    }
}