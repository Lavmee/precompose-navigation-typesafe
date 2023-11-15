package tech.annexflow.precompose.navigation.typesafe.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import moe.tlaster.precompose.navigation.BackStackEntry
import tech.annexflow.precompose.navigation.typesafe.Route
import kotlin.reflect.KClass

@OptIn(ExperimentalSerializationApi::class)
internal fun <T : Route> T.encodeToStringFromSerializersModule(): String {
    val serializer = routeSerializersModule.getPolymorphic(Route::class, value = this)
        ?: error("Need to register route type: ${this::class.simpleName}!")
    return this.encodeToString(serializer)
}

internal fun <T : Route> T.encodeToString(
    serializer: SerializationStrategy<T>
): String {
    val route = StringBuilder(createRouteBase(serializer))
    PathEncoder(route).encodeSerializableValue(serializer = serializer, value = this)
    return route.toString()
}

internal fun <T : Route> BackStackEntry.decodePath(
    deserializer: KSerializer<T>,
): T = BackStackEntryDecoder(backStackEntry = this).decodeSerializableValue(deserializer = deserializer)

internal fun lazyJson(serializationModule: SerializersModule): Lazy<Json> = lazy {
    Json { serializersModule = serializationModule }
}

internal fun <T : Route> registerRouteType(
    kClass: KClass<T>,
    serializer: KSerializer<T>,
) = addRouteModuleBuilder { subclass(kClass, serializer) }

@OptIn(ExperimentalSerializationApi::class)
internal fun createRouteBase(serializationStrategy: SerializationStrategy<*>): String =
    serializationStrategy.descriptor.serialName.substringAfterLast('.').lowercase()