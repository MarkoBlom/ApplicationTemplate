package aalto.kotlin.experiment.base.network

import aalto.kotlin.experiment.base.network.models.DatawireRequest
import aalto.kotlin.experiment.base.network.models.ResponseDto
import aalto.kotlin.experiment.base.network.models.rickandmorty.Episode
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


    /**
     * API Doc: https://rickandmortyapi.com/documentation/#episode
    {
        "id": 1,
        "name": "Pilot",
        "air_date": "December 2, 2013",
        "episode": "S01E01",
        "characters": ["https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/2", "https://rickandmortyapi.com/api/character/35", "https://rickandmortyapi.com/api/character/38", "https://rickandmortyapi.com/api/character/62", "https://rickandmortyapi.com/api/character/92", "https://rickandmortyapi.com/api/character/127", "https://rickandmortyapi.com/api/character/144", "https://rickandmortyapi.com/api/character/158", "https://rickandmortyapi.com/api/character/175", "https://rickandmortyapi.com/api/character/179", "https://rickandmortyapi.com/api/character/181", "https://rickandmortyapi.com/api/character/239", "https://rickandmortyapi.com/api/character/249", "https://rickandmortyapi.com/api/character/271", "https://rickandmortyapi.com/api/character/338", "https://rickandmortyapi.com/api/character/394", "https://rickandmortyapi.com/api/character/395", "https://rickandmortyapi.com/api/character/435"],
        "url": "https://rickandmortyapi.com/api/episode/1",
        "created": "2017-11-10T12:56:33.798Z"
    }
    */
    @GET( NetworkConstant.SINGLE_EPISODE )
    fun getEpisode(@Path("id") id : String) : Single<Response<Episode>>

    /**
     * Get multiple Episodes
     */
    @Headers( "Content-Type: application/json",
        "Accepts: application/json")
    @GET( NetworkConstant.SINGLE_EPISODE )
    fun getEpisodes(@Path("id") id : String) : Single<Response<List<Episode>>>

    // Add any other APIs here on ...
}