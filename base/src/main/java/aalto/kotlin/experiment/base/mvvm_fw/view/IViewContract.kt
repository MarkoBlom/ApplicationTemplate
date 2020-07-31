package aalto.kotlin.experiment.base.mvvm_fw.view

import aalto.kotlin.experiment.base.mvvm_fw.Action

/**
 * This is a contract between View and ViewModel:
 * View exposes only this API method for ViewModel(s) to consume i.a
 * callback from ViewModel -> View.
 */
interface IViewContract {

    /**
     * Action (request) from ViewModel to View
     * @param action
     */
    open fun onViewModelEvent(action : Action)

}