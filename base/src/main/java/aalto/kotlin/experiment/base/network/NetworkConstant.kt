package aalto.kotlin.experiment.base.network

/**
 * Various constant values for web service client and services
 */
class NetworkConstant {

        companion object {
            const val PROTOCOL = "https"

        /**
         * https://rickandmortyapi.com/api/episode/
         *
         * https://raw.githubusercontent.com/simonsickle/pfj-locations/master/locations.json
         */
        const val BASE_URL = "raw.githubusercontent.com/simonsickle/pfj-locations/master/"

        const val CONNECT_TIMEOUT = 1L // [min]
        //const val READ_TIMEOUT = 1L // [min]
        const val WRITE_TIMEOUT = 1L // [min]

        // Timeout reversal (TOR) related:
        // We wait 38 secs which is 3 sec longer than DW ClientTimeOut value (35s)
        //const val THRESHOLD_FOR_TIMEOUT_REVERSAL = 38000L // [ms]

        // We allow max 3 TOR retries
        const val MAX_RETRY_COUNT_TIMEOUT_REVERSAL = 3

        // Delay between attempted TOR retries, max 3 times (see above)
        const val DELAY_BETWEEN_TIMEOUT_REVERSAL = 35000L // [ms]

        // CA Root certificates, we apply their SHA1 fingerprints:

        // Root 3 VeriSign Class 3 Primary CA - G5 (exp 7/16/2036)
        // Serial Number: 18 da d1 9e 26 7d e8 bb 4a 21 58 cd cc 6b 3b 4a
        const val CA_SHA1_VeriSign_Class_3_Public_Primary_Certification_Authority_G5 = "sha1/4e b6 d5 78 49 9b 1c cf 5f 58 1e ad 56 be 3d 9b 67 44 a5 e5"

        // DigiCert Global Root CA (exp 11/10/2031)
        // Serial Number:  08 3b e0 56 90 42 46 b1 a1 75 6a c9 59 91 c7 4a
        const val CA_SHA1_DigiCert_Global_Root_CA = "sha1/A8985D3A65E5E5C4B2D7D66D40C6DD2FB19C5436"

        // DigiCert Global Root G2 (exp 1/15/2038)
        // Serial Number: ‎03 3a f1 e6 a7 11 a9 a0 bb 28 64 b1 1d 09 fa e5
        const val CA_SHA1_DigiCert_Global_Root_G2 = "sha1/df 3c 24 f9 bf d6 66 76 1b 26 80 73 fe 06 d1 cc 8d 4f 82 a4"

        const val SINGLE_EPISODE = "{id}"

        }
}