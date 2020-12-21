package aalto.kotlin.experiment.base.utilities

/**
 * By default object -class (Kotlin Singleton) doesn't have a constructor, only init block.
 *
 * This is a utility class to instantiate a Singleton with argument(s).
 */
open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {

    private var creator: ((A) -> T)? = creator

    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}