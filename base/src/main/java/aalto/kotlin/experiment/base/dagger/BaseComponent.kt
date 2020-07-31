package aalto.kotlin.experiment.base.dagger

import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.BaseRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BaseNetworkModule::class,
                        BaseRepositoryModule::class])

interface BaseComponent {
    val webApi: WebApi
    val baseRepository: BaseRepository
}