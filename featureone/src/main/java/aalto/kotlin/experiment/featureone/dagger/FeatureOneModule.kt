package aalto.kotlin.experiment.featureone.dagger

import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.featureone.FeatureOneViewModel
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.IViewModel
import dagger.Module
import dagger.Provides

@Module
class FeatureOneModule(var observer : IViewContract) {

    @Provides
    fun providesFeatureOneViewModel(model: BaseRepository,
                                    webApi: WebApi
    ) : IViewModel
            = FeatureOneViewModel(model, webApi, observer)

}