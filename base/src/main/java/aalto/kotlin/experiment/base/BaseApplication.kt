package aalto.kotlin.experiment.base

import aalto.kotlin.experiment.base.dagger.DaggerBaseComponent
import aalto.kotlin.experiment.base.utilities.ActivityLifecycles
import android.app.Application
import android.util.Log

/**
 * Application class itself
 */
class BaseApplication: Application() {

    private val mLifecycles = ActivityLifecycles()

    companion object {
        val baseComponent by lazy {
            DaggerBaseComponent.create()
        }
    }

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()

        // Lifecycle callbacks for cleaning the Model
        registerActivityLifecycleCallbacks(mLifecycles)
        mLifecycles.registerObserver(callback = { when(it) {
                0 -> {
                    Log.d("=MB=","BaseApplication : last activity destroyed -> purge data...")
                    baseComponent.baseRepository.purge()
                }

                1 -> Log.d("=MB=","BaseApplication : first activity created/resumed.")

                else -> {Log.d("=MB=","BaseApplication : activity count = $it")}
            }
        })
    }
}