package tech.annexflow.precompose.navigation.typesafe.internal

import moe.tlaster.precompose.navigation.QueryString
import kotlin.test.Test
import kotlin.test.assertEquals

class BackStackEntryDecoderTest {
    @Test
    fun decodeDataObject() {
        val expected = AppRoutes.Empty

        val pathMap: Map<String, String> = mapOf()
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = null)
            .decodeSerializableValue(AppRoutes.Empty.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClass() {
        val expected = AppRoutes.Simple(value = 1)

        val pathMap: Map<String, String> = mapOf("value" to "1")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = null)
            .decodeSerializableValue(AppRoutes.Simple.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassTwoArgs() {
        val expected = AppRoutes.TwoArgs(value = 1, text = "Test")

        val pathMap: Map<String, String> = mapOf("value" to "1", "text" to "Test")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = null)
            .decodeSerializableValue(AppRoutes.TwoArgs.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassNullableWithNull() {
        val expected = AppRoutes.Nullable(value = null)

        val pathMap: Map<String, String> = mapOf()
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = null)
            .decodeSerializableValue(AppRoutes.Nullable.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassNullableWithNotNull() {
        val expected = AppRoutes.Nullable(value = 1)

        val pathMap: Map<String, String> = mapOf()
        val queryString = QueryString("?value=1")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = queryString)
            .decodeSerializableValue(AppRoutes.Nullable.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassNullableTwoArgsWithNotNull() {
        val expected = AppRoutes.NullableTwoArgs(value = 1, text = "Test")

        val pathMap: Map<String, String> = mapOf()
        val queryString = QueryString("?value=1&text=Test")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = queryString)
            .decodeSerializableValue(AppRoutes.NullableTwoArgs.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassNullableTwoArgsWithNulls() {
        val expected = AppRoutes.NullableTwoArgs(value = null, text = null)

        val pathMap: Map<String, String> = mapOf()
        val queryString = QueryString("")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = queryString)
            .decodeSerializableValue(AppRoutes.NullableTwoArgs.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassNullableWithDefault() {
        val expected = AppRoutes.NullableWithDefault()

        val pathMap: Map<String, String> = mapOf()
        val queryString = QueryString("")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = queryString)
            .decodeSerializableValue(AppRoutes.NullableWithDefault.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassNullableWithDefaults() {
        val expected = AppRoutes.NullableWithDefaults()

        val pathMap: Map<String, String> = mapOf()
        val queryString = QueryString("?text=Test")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = queryString)
            .decodeSerializableValue(AppRoutes.NullableWithDefaults.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassWithComplexData() {
        val expected = AppRoutes.WithComplexData(data = AppRoutes.ComplexData(id = 2))

        val pathMap: Map<String, String> = mapOf("data" to """{"id":2}""")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = null)
            .decodeSerializableValue(AppRoutes.WithComplexData.serializer())

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun decodeDataClassWithComplexDataWithDefault() {
        val expected = AppRoutes.WithComplexData(data = AppRoutes.ComplexData())

        val pathMap: Map<String, String> = mapOf("data" to "{}")
        val actual = BackStackEntryDecoder(pathMap = pathMap, queryString = null)
            .decodeSerializableValue(AppRoutes.WithComplexData.serializer())

        assertEquals(expected = expected, actual = actual)
    }
}