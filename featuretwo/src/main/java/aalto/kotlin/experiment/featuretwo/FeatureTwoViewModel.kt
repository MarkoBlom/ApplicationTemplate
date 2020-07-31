package aalto.kotlin.experiment.featuretwo

import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import aalto.kotlin.experiment.base.network.NoConnectivityException
import aalto.kotlin.experiment.base.network.models.DatawireRequest
import aalto.kotlin.experiment.base.network.models.ResponseDto
import aalto.kotlin.experiment.base.network.models.rickandmorty.Episode
import aalto.kotlin.experiment.base.network.models.rickandmorty.Episodes
import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Response
import java.net.SocketTimeoutException

/**
 *
 */
class FeatureTwoViewModel(private val baseRepository: BaseRepository,
                          private val webApi: WebApi,
                          observer : IViewContract ) : BaseViewModel(observer) {

    lateinit var data : Episodes

    override fun onCreate() {
        Log.d("=MB=","FeatureTwoViewModel::onCreate() -> get data from Rick...")

        getData()
    }

    private fun getData() {

        // inform view to display a progress animation
        mObserver.get()?.onViewModelEvent( Action.create(Action.Type.PROGRESS_ANIM_SHOW))

        webApi.getEpisodes()
            .subscribeOn( Schedulers.io() )
            // back on UI thread
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe( object : SingleObserver<Response<Episodes>> {

                override fun onSubscribe(d: Disposable) {
                    Log.d("=MB=","  onSubscribe(..)");
                    mDisposables.add(d)
                }

                override fun onSuccess(response: Response<Episodes>) {
                    Log.d("=MB=", "  -> onSuccess(resp)")

                    // dismiss progress animation
                    mObserver.get()?.onViewModelEvent( Action.create(Action.Type.PROGRESS_ANIM_DISMISS))

                    onResponseReceived( response.body() )
                }

                override fun onError(throwable: Throwable) {
                    Log.d("=MB=", "  -> ****** onError(throwable): $throwable")

                    // hide progress animation
                    mObserver.get()?.onViewModelEvent( Action.create(Action.Type.PROGRESS_ANIM_DISMISS))

                    throwable.printStackTrace()

                    when( throwable) {
                        is SocketTimeoutException -> submitTimeoutReversalRequest()
                        is NoConnectivityException -> displayNoConnectivity()
                        else -> Log.d("=MB=","NOK, something else went wrong ???")
                    }
                }
            })
    }

    /**
     * Handle response here
     */
    private fun onResponseReceived(resp: Episodes? ) {
        Log.d("=MB=","      -> data: $resp.toString()")

        resp?.let { data = it }
    }


    /**
     * Inform client that there is no connectivty
     */
    private fun displayNoConnectivity() {
        val data = mapOf("TITLE" to "Error", "MSG" to "Check your connectivity and try again")
        mObserver.get()?.onViewModelEvent( Action.create(Action.Type.SHOW_WARNING_DIALOG, data))
    }


    /**
     * Make another web api call
     */
    private fun submitTimeoutReversalRequest() {

        // e.g. from anko lib
        doAsync {
            // do something else than web api call here
        }

        // - launch a Coroutine to execute in IO context,
        // - get handle to clean up any pending requests
        // - no exception handling
        mJob = launch(IO) {
            val data = fetchFromDb().await()
        }

        // handle response data here ....
    }


    /**
     *
     */
    private fun fetchFromDb(): Deferred<ResponseDto> = async(Dispatchers.IO) {
        ResponseDto("your data")
    }


}