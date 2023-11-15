package tech.annexflow.sample.navigation

import kotlinx.serialization.Serializable
import tech.annexflow.precompose.navigation.typesafe.Route

internal sealed interface AppRoutes : Route {
    @Serializable
    data object Home : AppRoutes

    @Serializable
    data class Scene(val id: Int) : AppRoutes

    @Serializable
    data class Dialog(val text: String) : AppRoutes

    @Serializable
    data class Floating(val isCenter: Boolean = true) : AppRoutes

    @Serializable
    sealed class Group(val id: Int) : Route {
        @Serializable
        data object First : Group(1)

        @Serializable
        data object Second : Group(2)
    }
}
