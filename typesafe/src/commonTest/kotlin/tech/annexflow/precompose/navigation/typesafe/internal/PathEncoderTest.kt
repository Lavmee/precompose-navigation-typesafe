package tech.annexflow.precompose.navigation.typesafe.internal

import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals

class PathEncoderTest {
    @Test
    fun encodeDataObject() {
        val url = StringBuilder()
        val model = AppRoutes.Empty
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.Empty>(),
            value = model
        )

        assertEquals(expected = "", actual = url.toString())
    }

    @Test
    fun encodeDataClass() {
        val url = StringBuilder()
        val model = AppRoutes.Simple(value = 1)
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.Simple>(),
            value = model
        )

        assertEquals(expected = "/1", actual = url.toString())
    }

    @Test
    fun encodeDataClassTwoArgs() {
        val url = StringBuilder()
        val model = AppRoutes.TwoArgs(value = 1, text = "Test")
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.TwoArgs>(),
            value = model
        )

        assertEquals(expected = "/1/Test", actual = url.toString())
    }

    @Test
    fun encodeDataClassNullableWithNull() {
        val url = StringBuilder()
        val model = AppRoutes.Nullable(value = null)
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.Nullable>(),
            value = model
        )

        assertEquals(expected = "", actual = url.toString())
    }

    @Test
    fun encodeDataClassNullableWithNotNull() {
        val url = StringBuilder()
        val model = AppRoutes.Nullable(value = 1)
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.Nullable>(),
            value = model
        )

        assertEquals(expected = "?value=1", actual = url.toString())
    }

    @Test
    fun encodeDataClassNullableTwoArgsWithNotNull() {
        val url = StringBuilder()
        val model = AppRoutes.NullableTwoArgs(value = 1, text = "Test")
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.NullableTwoArgs>(),
            value = model
        )

        assertEquals(expected = "?value=1&text=Test", actual = url.toString())
    }

    @Test
    fun encodeDataClassNullableTwoArgsWithNulls() {
        val url = StringBuilder()
        val model = AppRoutes.NullableTwoArgs(value = null, text = null)
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.NullableTwoArgs>(),
            value = model
        )

        assertEquals(expected = "", actual = url.toString())
    }

    @Test
    fun encodeDataClassNullableWithDefault() {
        val url = StringBuilder()
        val model = AppRoutes.NullableWithDefault()
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.NullableWithDefault>(),
            value = model
        )

        assertEquals(expected = "", actual = url.toString())
    }

    @Test
    fun encodeDataClassNullableWithDefaults() {
        val url = StringBuilder()
        val model = AppRoutes.NullableWithDefaults()
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.NullableWithDefaults>(),
            value = model
        )

        assertEquals(expected = "?text=Test", actual = url.toString())
    }

    @Test
    fun encodeDataClassWithComplexData() {
        val url = StringBuilder()
        val model = AppRoutes.WithComplexData(data = AppRoutes.ComplexData(2))
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.WithComplexData>(),
            value = model
        )

        assertEquals(expected = """/{"id":2}""", actual = url.toString())
    }

    @Test
    fun encodeDataClassWithComplexDataWithDefault() {
        val url = StringBuilder()
        val model = AppRoutes.WithComplexData(data = AppRoutes.ComplexData())
        PathEncoder(url).encodeSerializableValue(
            serializer = serializer<AppRoutes.WithComplexData>(),
            value = model
        )

        assertEquals(expected = "/{}", actual = url.toString())
    }
}