package tech.annexflow.precompose.navigation.typesafe

import kotlinx.serialization.KSerializer
import tech.annexflow.precompose.navigation.typesafe.internal.addRouteSerializer
import kotlin.reflect.KClass

fun <T : Route> registerRouteType(
    kClass: KClass<T>,
    serializer: KSerializer<T>,
) = addRouteSerializer(kClass, serializer)