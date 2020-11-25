package aalto.kotlin.experiment.base.dagger

import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.model.BaseRepository
import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [  ApplicationModule::class,
                        BaseNetworkModule::class,
                        BaseRepositoryModule::class])

interface BaseComponent {
    val context: Context
    val webApi: WebApi
    val baseRepository: BaseRepository
}