package aalto.kotlin.experiment.base.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    /**
     * Provides application context
     */
    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application
}