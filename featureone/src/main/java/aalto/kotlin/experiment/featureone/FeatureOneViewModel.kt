package aalto.kotlin.experiment.featureone

import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import aalto.kotlin.experiment.base.network.NoConnectivityException
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.network.models.pfj.PFJLocation
import aalto.kotlin.experiment.base.network.models.rickandmorty.Episode
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


/**
 * Constructor Injection
 */
class FeatureOneViewModel(private val model: BaseRepository,
                          private val webApi: WebApi
                           ) : BaseViewModel() {

    var mCounter = 5


    override fun onCreate() {
        Log.d("=MB=","FeatureOneViewModel::onCreate()")

        if (model.pfjLocations.isEmpty() )
            getPFJLocations()
    }

    /**
     * GET PFC locations for map
     */
    private fun getPFJLocations() {
        Log.d("=MB=", "FeatureOneViewModel::getPFJLocations()")

        // inform view to display a progress animation
        nextAction.value = Action.create(Action.Type.PROGRESS_ANIM_SHOW)

        webApi.getPFJLocations()
            .subscribeOn( Schedulers.io() )
            // back on UI thread
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe( object : SingleObserver<Response<ArrayList<PFJLocation>>> {

                override fun onSubscribe(d: Disposable) {
                    Log.d("=MB=","  onSubscribe(..)");
                    mDisposables.add(d)
                }

                override fun onSuccess(response: Response<ArrayList<PFJLocation>>) {
                    Log.d("=MB=", "  -> onSuccess(resp)")

                    // dismiss progress animation
                    nextAction.value = Action.create(Action.Type.PROGRESS_ANIM_DISMISS)
                    onLocationsDataReceived( response.body() )
                }

                override fun onError(throwable: Throwable) {
                    Log.d("=MB=", "  -> ****** onError(throwable): $throwable")

                    // hide progress animation
                    nextAction.value = Action.create(Action.Type.PROGRESS_ANIM_DISMISS)

                    throwable.printStackTrace()

                    when( throwable) {
                        is NoConnectivityException -> { /*displayNoConnectivity()*/ }
                        else -> Log.d("=MB=","NOK, something else went wrong ???")
                    }
                }
            })
    }


    private fun onLocationsDataReceived(resp: ArrayList<PFJLocation>? ) {

        // save to Repository
        resp?.let{
            model.pfjLocations = resp
        }

        nextAction.value = Action.create(Action.Type.PFJ_LOCATION_DATA_READY)
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
     * Goto Feature two -btn clicked, get data first
     */
    fun onNextClicked(view : View) {
        Log.d("=MB=","FeatureOneViewModel::onNextClicked()")

        // check if data is persisted in Model already
//        if( !model.episodes.isNullOrEmpty() ) {
//            Log.d("=MB=","      we've data already -> go to next screen")
//            // send action to view (LiveData) : Navigate to next screen
//            nextAction.setValue( Action.create(Action.Type.NEXT_SCREEN) )
//
//        } else {
//            Log.d("=MB=","      no data available -> next download it...")
//            getEpisodes()
//        }
    }

    /**
     * Get multiple Episodes
     */
    private fun getEpisodes() {
        Log.d("=MB=","FeatureOneViewModel::getEpisodes()")

        // inform view to display a progress animation
        nextAction.value = Action.create(Action.Type.PROGRESS_ANIM_SHOW)

        // Load the first ten episodes only...
        val episodes = "1,2,3,4,5,6,7,8,9,10"

        webApi.getEpisodes( episodes )
            .subscribeOn( Schedulers.io() )
            // back on UI thread
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe( object : SingleObserver<Response<ArrayList<Episode>>> {

                override fun onSubscribe(d: Disposable) {
                    Log.d("=MB=","  onSubscribe(..)");
                    mDisposables.add(d)
                }

                override fun onSuccess(response: Response<ArrayList<Episode>>) {
                    Log.d("=MB=", "  -> onSuccess(resp)")

                    // dismiss progress animation
                    nextAction.value = Action.create(Action.Type.PROGRESS_ANIM_DISMISS)
                    onResponseReceived( response.body() )
                }

                override fun onError(throwable: Throwable) {
                    Log.d("=MB=", "  -> ****** onError(throwable): $throwable")

                    // hide progress animation
                    nextAction.value = Action.create(Action.Type.PROGRESS_ANIM_DISMISS)

                    throwable.printStackTrace()

                    when( throwable) {
                        is NoConnectivityException -> { /*displayNoConnectivity()*/ }
                        else -> Log.d("=MB=","NOK, something else went wrong ???")
                    }
                }
            })
    }

    /**
     * Handle response here
     */
    private fun onResponseReceived(resp: ArrayList<Episode>? ) {
        Log.d("=MB=","      -> data: $resp.toString()")

        model.episodes = if( resp.isNullOrEmpty() ){
            Log.d("=MB=","      -> no data, not much to do ???")
            null
        } else {
            Log.d("=MB=","      -> we got data, stored in Model.")
            // Store data to Model
            resp
       }

        if( !model.episodes.isNullOrEmpty() ) {
            // navigate to next screen
            nextAction.value = Action.create(Action.Type.NEXT_SCREEN)
        }
    }
}