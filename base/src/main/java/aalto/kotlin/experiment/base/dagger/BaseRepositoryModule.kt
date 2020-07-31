package aalto.kotlin.experiment.base.dagger

import aalto.kotlin.experiment.base.BaseRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseRepositoryModule {
    @Provides
    @Singleton
    fun provideBaseRepository() = BaseRepository("from BaseRepositoryModule")
}