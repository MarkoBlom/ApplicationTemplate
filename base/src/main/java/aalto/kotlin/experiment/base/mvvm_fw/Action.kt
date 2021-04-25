package aalto.kotlin.experiment.base.mvvm_fw

/**
 * Created by marko on 8/24/2017.
 *
 * Presents an Action upon a View to process e.g. show progress animation/bar, start activity etc. anything you specify in action
 * Action always has a Type and optionally Data
 *
 * @param type Action type Enumeration, e.g. show Progress animation, Hide Progress animation, etc.
 * @param data optional data associated with Action. Put here any extra data that needs to be passed to View
 */
class Action protected constructor(val type: Type,
                                   val data: Map<String, Any>? ) {
    enum class Type {
        PROGRESS_ANIM_SHOW,
        PROGRESS_ANIM_DISMISS,
        NEXT_SCREEN,
        VALID_PHONE_NUMBER_ENTERED,
        NEXT_TAB,
        BACK_FROM_TAB,
        PFJ_LOCATION_DATA_READY,
        SHOW_SNACK_BAR,
        GO_TO_TRANSACTIONS_SCREEN,
        GO_TO_REWARDS,
        GO_TO_PAYMENT_FIELDS_ENTRY,
        GO_TO_PAYMENT_SUMMARY,
        SHOW_OK_DIALOG,
        GO_HOME,
        SHOW_ERROR_DIALOG,
        SHOW_WARNING_DIALOG,
        ERROR_CONNECTION_TIMEOUT,
        ERROR_REENTER_CARD
    }

    companion object {

        fun create(type: Type) = Action(type, null)

        fun create(type: Type,
                   data: Map<String, Any>?) = Action(type, data)
    }
}