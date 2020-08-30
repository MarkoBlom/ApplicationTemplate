package aalto.kotlin.experiment.applicationtemplate.dagger

import aalto.kotlin.experiment.applicationtemplate.MainActivity
import aalto.kotlin.experiment.base.dagger.ActivityScope
import aalto.kotlin.experiment.base.dagger.BaseComponent
import dagger.Component

@ActivityScope
@Component(dependencies = [BaseComponent::class],
    modules = [MainModule::class])
interface MainComponent {

    // Injection target:
    fun inject(mainActivity: MainActivity)


}