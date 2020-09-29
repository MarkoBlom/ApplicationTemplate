package aalto.kotlin.experiment.base.mvvm_fw.view

import aalto.kotlin.experiment.base.mvvm_fw.Action

/**
 * This is a contract between View and ViewModel:
 * MutableLiveData handler
 */
interface IViewContract {

    /**
     * Function to handle MutableLiveData updates from ViewModel
     * @param action Action (request) from ViewModel to View
     */
    open fun onNextAction(action : Action)

}