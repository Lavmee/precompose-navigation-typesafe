package tech.annexflow.precompose.navigation.typesafe

import tech.annexflow.precompose.navigation.typesafe.internal.AppRoutes
import kotlin.test.Test
import kotlin.test.assertEquals

class RouteGenerationTest {
    @Test
    fun generateFromDataObject() {
        assertEquals(expected = "empty", actual = AppRoutes.Empty.generateRoutePattern())
    }

    @Test
    fun generateFromDataClass() {
        assertEquals(
            expected = "simple/1",
            actual = AppRoutes.Simple(value = 1).generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassTwoArgs() {
        assertEquals(
            expected = "two_args/1/Test",
            actual = AppRoutes.TwoArgs(value = 1, text = "Test").generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassNullableWithNull() {
        assertEquals(
            expected = "nullable",
            actual = AppRoutes.Nullable(value = null).generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassNullableWithNotNull() {
        assertEquals(
            expected = "nullable?value=1",
            actual = AppRoutes.Nullable(value = 1).generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassNullableTwoArgsWithNotNull() {
        assertEquals(
            expected = "nullable_two_args?value=1&text=Test",
            actual = AppRoutes.NullableTwoArgs(value = 1, text = "Test").generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassNullableTwoArgsWithNulls() {
        assertEquals(
            expected = "nullable_two_args",
            actual = AppRoutes.NullableTwoArgs(value = null, text = null).generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassNullableWithDefault() {
        assertEquals(
            expected = "nullable_with_default",
            actual = AppRoutes.NullableWithDefault().generateRoutePattern()
        )
    }

    @Test
    fun generateFromDataClassNullableWithDefaults() {
        assertEquals(
            expected = "nullable_with_defaults?text=Test",
            actual = AppRoutes.NullableWithDefaults().generateRoutePattern()
        )
    }

    @Test
    fun generateDataClassWithComplexData() {
        assertEquals(
            expected = """with_complex_data/{"id":2}""",
            actual = AppRoutes.WithComplexData(
                data = AppRoutes.ComplexData(id = 2)
            ).generateRoutePattern()
        )
    }

    @Test
    fun generateDataClassWithComplexDataWithDefault() {
        assertEquals(
            expected = """with_complex_data/{}""",
            actual = AppRoutes.WithComplexData(
                data = AppRoutes.ComplexData()
            ).generateRoutePattern()
        )
    }

}