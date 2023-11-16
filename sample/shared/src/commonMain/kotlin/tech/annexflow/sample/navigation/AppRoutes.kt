package tech.annexflow.sample.navigation

import kotlinx.serialization.Serializable
import tech.annexflow.precompose.navigation.typesafe.Route

internal sealed interface AppRoutes : Route {
    @Serializable
    data object Home : AppRoutes

    @Serializable
    data class Scene(val id: Int?) : AppRoutes

    @Serializable
    data class Dialog(val text: String) : AppRoutes

    @Serializable
    data class Floating(val isCenter: Boolean = true) : AppRoutes

    /**
     * Sealed classes & interfaces are not fully supported now.
     **/
    @Serializable
    open class Group(val id: Int? = 5) : AppRoutes {
        @Serializable
        data object First : Group(null)

        @Serializable
        data object Second : Group(2)

        override fun toString(): String {
            return "Group(id=$id)"
        }
    }
}
