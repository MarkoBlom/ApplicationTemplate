package aalto.kotlin.experiment.base.dagger

import aalto.kotlin.experiment.base.model.BaseRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseRepositoryModule {

    @Provides
    @Singleton
    fun provideBaseRepository() = BaseRepository("from BaseRepositoryModule")

/*
    @Provides
    @Singleton
    fun provideBaseRepository(  database: AntonDatabase,
                                sharedPreferences: SharedPreferences)
            = BaseDataRepository(database, sharedPreferences)

    /**
     * Provides handle to Room database
     */
    @Provides
    @Singleton
    fun provideAntonDatabase(): AntonDatabase =

        Room.databaseBuilder(
            application,
            AntonDatabase::class.java, "anton-database.db"
        ).build()


    /**
     * Provides handle to shared preferences
     */
    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(application)
}
*/

}