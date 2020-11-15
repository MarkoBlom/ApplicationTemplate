package aalto.kotlin.experiment.base.network.models.pfj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PFJLocation(
    @SerialName("name") val name: String,
    @SerialName("lat") val lat : String,
    @SerialName("long") val long : String) {
}