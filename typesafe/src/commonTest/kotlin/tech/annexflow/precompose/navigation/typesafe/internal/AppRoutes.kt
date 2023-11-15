package tech.annexflow.precompose.navigation.typesafe.internal

import kotlinx.serialization.Serializable
import tech.annexflow.precompose.navigation.typesafe.Route

sealed interface AppRoutes : Route {
    @Serializable
    data object Empty : AppRoutes

    @Serializable
    data class Simple(val value: Int) : AppRoutes

    @Serializable
    data class TwoArgs(val value: Int, val text: String) : AppRoutes

    @Serializable
    data class Nullable(val value: Int?) : AppRoutes

    @Serializable
    data class NullableTwoArgs(val value: Int?, val text: String?) : AppRoutes

    @Serializable
    data class NullableWithDefault(val value: Int? = null) : AppRoutes

    @Serializable
    data class NullableWithDefaults(val value: Int? = null, val text: String? = "Test") : AppRoutes

    @Serializable
    data class WithComplexData(val data: ComplexData) : AppRoutes

    @Serializable
    data class ComplexData(val id: Int = 1)
}