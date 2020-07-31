package aalto.kotlin.experiment.applicationtemplate

import aalto.kotlin.experiment.base.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import aalto.kotlin.experiment.base.network.WebApi
import android.util.Log
import android.view.View

/**
 *
 */
class MainViewModel(private val baseRepository: BaseRepository,
                    private val webApi: WebApi,
                    observer : IViewContract ) : BaseViewModel(observer){

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()

        Log.d("=MB=","MainViewModel::onCreate()")
    }

    /**
     *
     */
    override fun onDestroy() {
        Log.d("=MB=","MainViewModel::onDestroy()")

        super.onDestroy()
    }

    /**
     * Goto Feature One -btn clicked
     */
    fun onNextClicked(view : View) {
        Log.d("=MB=","MainViewModel::onNextClicked()")

        mObserver.get()?.onViewModelEvent( Action.create(Action.Type.NEXT_SCREEN))
    }

}