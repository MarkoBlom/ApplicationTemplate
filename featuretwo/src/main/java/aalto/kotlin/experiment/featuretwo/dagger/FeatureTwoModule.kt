package aalto.kotlin.experiment.featuretwo.dagger

import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import dagger.Module
import dagger.Provides

@Module
class FeatureTwoModule {

    @Provides
    fun providesFeatureTwoViewModel(model: BaseRepository,
                                    webApi: WebApi
    ) : IViewModel = FeatureTwoViewModel(model, webApi)

}