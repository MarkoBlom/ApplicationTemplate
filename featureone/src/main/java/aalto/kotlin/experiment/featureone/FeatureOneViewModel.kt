package aalto.kotlin.experiment.featureone

import aalto.kotlin.experiment.base.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import aalto.kotlin.experiment.base.network.WebApi
import android.os.Bundle
import android.util.Log
import android.view.View


/**
 * Constructor Injection
 */
class FeatureOneViewModel(private val baseRepository: BaseRepository,
                          private val webApi: WebApi,
                          observer : IViewContract ) : BaseViewModel(observer) {

    var mCounter = 5

    override fun onCreate() {
        Log.d("=MB=","FeatureOneViewModel::onCreate()")

        //mObserver.get()?.onViewModelEvent( Action.create(Action.Type.PROGRESS_ANIM_DISMISS))
    }

    override fun onDestroy() {
        Log.d("=MB=","FeatureOneViewModel::onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle?) {

        mCounter+=5;

        Log.d("=MB=","FeatureOneViewModel::onSaveInstanceState(), saving: mCounter = $mCounter")

        // restore value:
        outState?.run {
            putInt("GAME_STATE_KEY", mCounter)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        mCounter = savedInstanceState?.getInt("GAME_STATE_KEY") ?: 100

        Log.d("=MB=","FeatureOneViewModel::onRestoreInstanceState(), restore : mCounter = $mCounter")
    }

    /**
     * Goto Feature two -btn clicked
     */
    fun onNextClicked2(view : View) {
        Log.d("=MB=","FeatureOneViewModel::onNextClicked2()")

        mObserver.get()?.onViewModelEvent( Action.create(Action.Type.NEXT_SCREEN))
    }
}