package tech.annexflow.precompose.navigation.typesafe.internal

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
internal class PathDecoder(
    private val pathMap: Map<String, String>,
) : AbstractDecoder() {
    override val serializersModule: SerializersModule by lazy { routeSerializersModule }
    private val json by lazyJson(serializersModule)
    private var isMain = true
    private var elementsCount = 0
    private var elementIndex = 0
    private var elementName = ""

    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        return if (isMain || (deserializer.descriptor.kind is PrimitiveKind)) {
            super.decodeSerializableValue(deserializer)
        } else {
            json.decodeFromString(deserializer, decodeString())
        }
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        while (elementIndex < elementsCount) {
            elementName = descriptor.getElementName(elementIndex)
            elementIndex++
            if (pathMap.containsKey(elementName)) {
                return elementIndex - 1
            }
        }
        return CompositeDecoder.DECODE_DONE
    }

    override fun decodeNotNullMark(): Boolean =
        pathMap[elementName] != null && pathMap[elementName] != "null"

    override fun decodeNull(): Nothing? = null
    override fun decodeInt(): Int = pathMap[elementName]!!.toInt()
    override fun decodeLong(): Long = pathMap[elementName]!!.toLong()
    override fun decodeFloat(): Float = pathMap[elementName]!!.toFloat()
    override fun decodeBoolean(): Boolean = pathMap[elementName]!!.toBooleanStrict()
    override fun decodeString(): String = pathMap[elementName]!!
    override fun decodeDouble(): Double = pathMap[elementName]!!.toDouble()
    override fun decodeByte(): Byte = pathMap[elementName]!!.toByte()
    override fun decodeShort(): Short = pathMap[elementName]!!.toShort()
    override fun decodeChar(): Char = pathMap[elementName]!!.first()
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = decodeInt()

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        elementsCount = descriptor.elementsCount
        isMain = false
        return this
    }
}