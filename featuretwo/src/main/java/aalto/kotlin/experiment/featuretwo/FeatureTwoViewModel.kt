package aalto.kotlin.experiment.featuretwo

import aalto.kotlin.experiment.base.mvvm_fw.view.IViewContract
import aalto.kotlin.experiment.base.network.WebApi
import aalto.kotlin.experiment.base.model.BaseRepository
import aalto.kotlin.experiment.base.mvvm_fw.Action
import aalto.kotlin.experiment.base.mvvm_fw.viewmodel.BaseViewModel
import aalto.kotlin.experiment.base.network.NoConnectivityException
import aalto.kotlin.experiment.base.network.models.ResponseDto
import aalto.kotlin.experiment.base.network.models.rickandmorty.Episode
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
import org.jetbrains.anko.doAsync
import retrofit2.Response
import java.net.SocketTimeoutException

private const val MAX_NUM_OF_ITEMS = 41

/**
 *
 */
class FeatureTwoViewModel(private val model: BaseRepository,
                          private val webApi: WebApi,
                          observer : IViewContract ) : BaseViewModel(observer) {

    private var mDownloadInProgress = false

    // Data to be shown in RecyclerView, stored in Model -class in previous screen
    lateinit var data : ArrayList<Episode>

    lateinit var adapter : TestAdapter

    override fun onCreate() {
        Log.d("=MB=","FeatureTwoViewModel::onCreate()")

        // when we get here we have data so it's ok
        data = model.episodes!!

        adapter = TestAdapter(this)

        adapter.notifyDataSetChanged()
    }


    /**
     * Get multiple Episodes
     */
    private fun getMoreEpisodes( episodes : String ) {
        Log.d("=MB=","FeatureTwoViewModel::getMoreEpisodes($episodes)");

        webApi.getEpisodes( episodes )
            .subscribeOn( Schedulers.io() )
            // back on UI thread
            .observeOn( AndroidSchedulers.mainThread() )
            .subscribe( object : SingleObserver<Response<ArrayList<Episode>>> {

                override fun onSubscribe(d: Disposable) {
                    Log.d("=MB=","  -> onSubscribe(..)");
                    mDisposables.add(d)
                }

                override fun onSuccess(response: Response<ArrayList<Episode>>) {
                    Log.d("=MB=", "  -> onSuccess(resp)")

                    onResponseReceived( response.body() )
                }

                override fun onError(throwable: Throwable) {
                    Log.d("=MB=", "  -> ****** onError(throwable): $throwable")

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
    private fun onResponseReceived(resp: ArrayList<Episode>? ) {

        resp?.let {
            data.addAll(it)
            adapter.notifyDataSetChanged()
        }

        Log.d("=MB=","      onResponseReceived -> new data.size = ${data.size}")

        mDownloadInProgress = false
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

    fun latestPositionInRecyclerView( currentPosition : Int) {

        // We are downloading already
        if( mDownloadInProgress || data.size == MAX_NUM_OF_ITEMS )
            return

        // current data size
        val size = data.size

        // Let's anticipate little bit: If user has scrolled down to
        // the second last item in the list then we download more, e.g. next 5 items or so
        if( size < MAX_NUM_OF_ITEMS && currentPosition >= size-2 ){

            mDownloadInProgress = true

            var items = ""
                if (size == 35) {
                    items = "36,37,38,39,40,41"
                } else {
                    for (index in size+1 until size+5) {
                        items += "${index},"
                    }
                    items += "${size + 5}"
                }

            getMoreEpisodes(items)
        }
    }
}