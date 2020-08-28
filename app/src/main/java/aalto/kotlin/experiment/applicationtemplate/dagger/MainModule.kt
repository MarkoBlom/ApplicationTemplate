package aalto.kotlin.experiment.applicationtemplate.dagger

import aalto.kotlin.experiment.applicationtemplate.MainViewModel
import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(var observer : IViewContract) {

    @Provides
    fun providesMainViewModel(baseRepository: BaseRepository,
                              webApi: WebApi
    ) : IViewModel
            = MainViewModel(baseRepository, webApi, observer)

}