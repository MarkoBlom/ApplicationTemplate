package aalto.kotlin.experiment.featureone.dagger

import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.featureone.FeatureOneViewModel
import aalto.kotlin.experiment.base.BaseRepository
import aalto.kotlin.experiment.base.network.WebApi
import com.elyeproj.base.mvvm_fw.viewmodel.IViewModel
import dagger.Module
import dagger.Provides

@Module
class FeatureOneModule(var observer : IViewContract) {

    @Provides
    fun providesFeatureOneViewModel(baseRepository: BaseRepository,
                                    webApi: WebApi
    ) : IViewModel
            = FeatureOneViewModel(baseRepository, webApi, observer)

}