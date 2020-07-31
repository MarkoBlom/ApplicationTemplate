package aalto.kotlin.experiment.featuretwo.dagger

import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import aalto.kotlin.experiment.base.BaseRepository
import aalto.kotlin.experiment.base.network.WebApi
import com.elyeproj.base.mvvm_fw.viewmodel.IViewModel
import dagger.Module
import dagger.Provides

@Module
class FeatureTwoModule(var observer : IViewContract) {

    @Provides
    fun providesFeatureTwoViewModel(baseRepository: BaseRepository,
                                    webApi: WebApi
    ) : IViewModel
            = FeatureTwoViewModel(baseRepository, webApi, observer)

}