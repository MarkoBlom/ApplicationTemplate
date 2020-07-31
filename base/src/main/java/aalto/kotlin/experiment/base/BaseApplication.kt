package aalto.kotlin.experiment.base

import aalto.kotlin.experiment.base.dagger.DaggerBaseComponent
import android.app.Application

class BaseApplication: Application() {

    companion object {

        val baseComponent by lazy {
            DaggerBaseComponent.create()
        }
    }
}