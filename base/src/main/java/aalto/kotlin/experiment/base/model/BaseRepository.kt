package aalto.kotlin.experiment.base.model

import aalto.kotlin.experiment.base.network.models.rickandmorty.Episode

/**
 * This class represents the Model in MVVM -design pattern which this application applies
 *
 * Responsibility of this model is ONLY to persist data in different ways.
 * It can utilize e.g. file system, shared preferences, dB or just runtime data.
 *
 * This class is not responsible for retrieving data via different APIs, that the role of
 * ViewModel.
 * If you've multiple classes refreshing/pulling fresh data then you can apply
 * DataRepository-pattern i.e. providing APIs to do that w/o duplicating same code
 * in multiple classes/modules
 *
 */
class BaseRepository(val name: String) {

    override fun toString(): String {
        return "BaseRepository $name ${super.toString()} "
    }

    /**
     * List of Episodes (runtime data)
     */
    var episodes : List<Episode>? = null

    // TODO : add data cleanup functionality
}
