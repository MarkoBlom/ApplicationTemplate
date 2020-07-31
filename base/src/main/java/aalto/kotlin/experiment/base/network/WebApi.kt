package aalto.kotlin.experiment.base.network

import aalto.kotlin.experiment.base.network.models.DatawireRequest
import aalto.kotlin.experiment.base.network.models.ResponseDto
import aalto.kotlin.experiment.base.network.models.rickandmorty.Episodes
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Web service APIs
 */
interface WebApi {

    /**
     * Api for sending a transaction request
     */
    @POST("rc") // PROD path
    @Headers("Accept: text/xml, multipart/related",
        "Content-Type: text/xml",
        "Connection: Keep-Alive",
        "Cache-Control: no-cache")
    fun transactionRequestSingle(@Header("User-Agent") appName: String,
                                 @Body requestBody: DatawireRequest
    ): Single<Response<ResponseDto>>



    @GET
    //fun getEpisodes(@Path("id") id : Array<String>) : Single<Response<Episode>>
    fun getEpisodes() : Single<Response<Episodes>>

    // Add any other APIs here on ...
}