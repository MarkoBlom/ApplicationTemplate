package aalto.kotlin.experiment.base.utilities

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityLifecycles : Application.ActivityLifecycleCallbacks {

    private var mCallback: ((Int) -> Unit)? = null

    private var mRunningActivityCount : Int? = null
        set(value) {
            field = value
            value?.let {
                // We invoke callback only when:
                // 1) the first activity is created/resumed i.e. count = 1 or
                // 2) last activity is destroyed i.e. count = 0
                //if( value==0 || value==1 )
                mCallback?.invoke(it)
            }
        }

    fun registerObserver(callback: ((Int) -> Unit)) {
        mCallback = callback
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

        mRunningActivityCount = if( mRunningActivityCount == null )
            1 // first activity
        else
            mRunningActivityCount!! + 1
    }

    override fun onActivityStarted(p0: Activity) {}

    override fun onActivityResumed(p0: Activity) {}

    override fun onActivityPaused(p0: Activity) {}

    override fun onActivityStopped(p0: Activity) {}

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

    override fun onActivityDestroyed(p0: Activity) { mRunningActivityCount = mRunningActivityCount!! - 1 }
}