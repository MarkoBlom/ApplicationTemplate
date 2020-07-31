package aalto.kotlin.experiment.base.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Prints TLS Version and Cipher Suite for SSL Calls through OkHttp3
 */
class SSLHandshakeInterceptor : Interceptor {

    companion object {
        const val TAG = "OkHttp"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request());
        printTlsAndCipherSuiteInfo(response);
        return response;
    }

    private fun printTlsAndCipherSuiteInfo(response : Response) {

        response.apply {
            handshake?.apply {
                Log.d("=MB=", "Used TLS version: $tlsVersion, CipherSuite: $cipherSuite")
            }
        }
    }
}