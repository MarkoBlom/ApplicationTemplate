package aalto.kotlin.experiment.base.network.models.rickandmorty

data class Info(    val count : Int,
                    val pages : Int,
                    val next : String?,
                    val prev : String ?) {
}