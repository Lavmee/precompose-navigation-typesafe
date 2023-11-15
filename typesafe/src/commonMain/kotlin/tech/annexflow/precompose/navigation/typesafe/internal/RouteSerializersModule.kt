package tech.annexflow.precompose.navigation.typesafe.internal

import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.polymorphic
import tech.annexflow.precompose.navigation.typesafe.Route

internal var routeSerializersModule: SerializersModule = EmptySerializersModule()
    private set

internal fun addRouteModuleBuilder(
    builder: PolymorphicModuleBuilder<Route>.() -> Unit,
) {
    routeSerializersModule += SerializersModule { polymorphic(Route::class) { builder() } }
}