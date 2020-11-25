package aalto.kotlin.experiment.base

import aalto.kotlin.experiment.base.dagger.ApplicationModule
import aalto.kotlin.experiment.base.dagger.BaseComponent
import aalto.kotlin.experiment.base.dagger.DaggerBaseComponent
import aalto.kotlin.experiment.base.utilities.ActivityLifecycles
import android.app.Application
import android.util.Log
import com.squareup.leakcanary.LeakCanary

/**
 * Application class itself
 */
class BaseApplication: Application() {

    private val mLifecycles = ActivityLifecycles()

    companion object {
        lateinit var baseComponent: BaseComponent
    }

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()

        //init base component
        baseComponent = DaggerBaseComponent.builder()
            .applicationModule( ApplicationModule(this) ) // context provider
            .build()


        // Memory leak detection by using LeakCanary
        // (Profiler tool is invented too but LeakCanary is
        // more 'visual')
        LeakCanary.install(this)

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