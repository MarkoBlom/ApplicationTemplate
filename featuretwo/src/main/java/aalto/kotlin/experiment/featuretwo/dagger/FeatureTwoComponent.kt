package aalto.kotlin.experiment.featuretwo.dagger

import aalto.kotlin.experiment.base.dagger.ActivityScope
import aalto.kotlin.experiment.base.dagger.BaseComponent
import aalto.kotlin.experiment.featuretwo.FeatureTwoActivity
import dagger.Component

/**
 * See the dependency to BaseComponent, it injects the common classes
 * BaseNetwork and BaseRepository
 */
@ActivityScope
@Component(dependencies = [BaseComponent::class],
    modules = [FeatureTwoModule::class])
interface FeatureTwoComponent {

    // Injection target
    fun inject( featureTwoActivity: FeatureTwoActivity)
}