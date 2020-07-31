package aalto.kotlin.experiment.base.network.models.rickandmorty

/*
    "id": 3,
    "name": "Anatomy Park",
    "air_date": "December 16, 2013",
    "episode": "S01E03",
    "characters": ["https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/2", "https://rickandmortyapi.com/api/character/12", "https://rickandmortyapi.com/api/character/17", "https://rickandmortyapi.com/api/character/38", "https://rickandmortyapi.com/api/character/45", "https://rickandmortyapi.com/api/character/96", "https://rickandmortyapi.com/api/character/97", "https://rickandmortyapi.com/api/character/98", "https://rickandmortyapi.com/api/character/99", "https://rickandmortyapi.com/api/character/100", "https://rickandmortyapi.com/api/character/101", "https://rickandmortyapi.com/api/character/108", "https://rickandmortyapi.com/api/character/112", "https://rickandmortyapi.com/api/character/114", "https://rickandmortyapi.com/api/character/169", "https://rickandmortyapi.com/api/character/175", "https://rickandmortyapi.com/api/character/186", "https://rickandmortyapi.com/api/character/201", "https://rickandmortyapi.com/api/character/268", "https://rickandmortyapi.com/api/character/300", "https://rickandmortyapi.com/api/character/302", "https://rickandmortyapi.com/api/character/338", "https://rickandmortyapi.com/api/character/356"],
    "url": "https://rickandmortyapi.com/api/episode/3",
    "created": "2017-11-10T12:56:34.022Z"
*/

data class Episode(val id : Int,
                    val name : String,
                    val air_date : String,
                    val episode : String,
                    val characters : Array<String>,
                   val url : String,
                   val created : String ) {
    operator fun invoke(episode: Episode) {

    }
}