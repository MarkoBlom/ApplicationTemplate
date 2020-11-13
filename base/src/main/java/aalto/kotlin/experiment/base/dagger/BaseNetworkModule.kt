package aalto.kotlin.experiment.base.dagger

import aalto.kotlin.experiment.base.network.NetworkConstant
import aalto.kotlin.experiment.base.network.NetworkConstant.Companion.CA_SHA1_DigiCert_Global_Root_CA
import aalto.kotlin.experiment.base.network.NetworkConstant.Companion.CA_SHA1_DigiCert_Global_Root_G2
import aalto.kotlin.experiment.base.network.NetworkConstant.Companion.CA_SHA1_VeriSign_Class_3_Public_Primary_Certification_Authority_G5
import aalto.kotlin.experiment.base.network.SSLHandshakeInterceptor
import aalto.kotlin.experiment.base.network.WebApi
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class BaseNetworkModule {

    companion object {
        private const val NAME_BASE_URL = "NAME_BASE_URL"
    }

    /**
     *
     */
    @Provides
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString() = "${NetworkConstant.PROTOCOL}://${NetworkConstant.BASE_URL}"


    /**
     * provides OkHttp3 client
     */
    @Provides
    @Singleton
    fun provideHttpClient( @Named(NAME_BASE_URL) baseUrl : String) : OkHttpClient {

        // For logging request/response body
        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        // Certificates (SHA1 fingerprints) for 'handshake' with DataWire frontend
//        val certPinner = CertificatePinner.Builder()
//            .add(baseUrl, CA_SHA1_VeriSign_Class_3_Public_Primary_Certification_Authority_G5)
//            .add(baseUrl, CA_SHA1_DigiCert_Global_Root_CA)
//            .add(baseUrl, CA_SHA1_DigiCert_Global_Root_G2)
//            .build();
//
//        // Ciphers to use with TLS1.2 - Seems that TLSv1.3 isn't supported by DW frontend (host connection refused)
//        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//            .tlsVersions(TlsVersion.TLS_1_2)
//            .cipherSuites(
//                // API level 20+, FD: mandatory in order to connect to staging URL:
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
//                // API level 20+, FD: mandatory in order to connect to staging URL , ONLY THIS ONE IS FOUND DURING SSL HANDSHAKE:
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                // FD: 'suggest to support below additional 2 ciphers since not all of our servers are updated to support mandatory ciphers yet.
//                // We are in process of updating those'.
//                CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,   // API level 20-25
//                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)   // API level 20-25
//            .build();

        // Client itself
        val okHttpClient = OkHttpClient.Builder()
            //.addInterceptor( CheckNetworkConnectionInterceptor(contextRef) )  NO CONNECTIVITY DETECTION
            //.addNetworkInterceptor( SSLHandshakeInterceptor() ) NO TLS VERSION
            .addNetworkInterceptor( logging )
            //.certificatePinner( certPinner ) NO SSL HANDSHAKE
            //.connectionSpecs( Collections.singletonList(spec) ) NO CIPHERS FOR DATA ENCRYPTION
            //.connectTimeout( NetworkConstant.CONNECT_TIMEOUT, TimeUnit.MINUTES)
            //.writeTimeout( NetworkConstant.WRITE_TIMEOUT, TimeUnit.MINUTES)
            //.readTimeout( NetworkConstant.THRESHOLD_FOR_TIMEOUT_REVERSAL, TimeUnit.MILLISECONDS) // timeout for reading data from connected socket
            .build()

        return okHttpClient
    }

    /**
     * Provides Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(@Named(NAME_BASE_URL) baseUrl : String,
                        okHttpClient : OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            //XML converter: .addConverterFactory( SimpleXmlConverterFactory.createNonStrict( Persister(AnnotationStrategy())))
            .addConverterFactory( GsonConverterFactory.create() )
            .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideWebApi(retrofit: Retrofit) = retrofit.create(WebApi::class.java)
}