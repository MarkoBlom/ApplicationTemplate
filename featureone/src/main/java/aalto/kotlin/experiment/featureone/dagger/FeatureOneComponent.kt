package aalto.kotlin.experiment.featureone.dagger

import aalto.kotlin.experiment.base.dagger.ActivityScope
import aalto.kotlin.experiment.base.dagger.BaseComponent
import aalto.kotlin.experiment.featureone.FeatureOneActivity
import dagger.Component

/**
 * See the dependency to BaseComponent, it injects the common classes
 * BaseNetwork and BaseRepository
 */
@ActivityScope
@Component(dependencies = [BaseComponent::class],
    modules = [FeatureOneModule::class])
interface FeatureOneComponent {

    // Injection target
    fun inject( featureOneActivity: FeatureOneActivity )
}