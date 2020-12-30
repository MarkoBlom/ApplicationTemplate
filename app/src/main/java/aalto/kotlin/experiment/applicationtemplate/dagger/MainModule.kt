package aalto.kotlin.experiment.applicationtemplate.dagger

import aalto.kotlin.experiment.applicationtemplate.MainViewModel
import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class MainModule() {

    @Provides
    fun providesMainViewModel(baseRepository: BaseRepository,
                              webApi: WebApi,
                              context: Context
    ) : IViewModel = MainViewModel(baseRepository, webApi, context)

}