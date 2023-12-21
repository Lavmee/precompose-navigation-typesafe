package tech.annexflow.precompose.navigation.typesafe.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class UrlEncoderTest {

    @Test
    fun basic() {
        val url = "https://precompose.annexflow.tech/id/1/2/3.jpg?hmac=test_ma-TEST"
        val encodedUrl = UrlEncoder.encode(url)
        assertEquals(
            expected = "https%3A%2F%2Fprecompose.annexflow.tech%2Fid%2F1%2F2%2F3.jpg%3Fhmac%3Dtest_ma-TEST",
            actual = encodedUrl
        )
        val decodedUrl = UrlEncoder.decode(encodedUrl)
        assertEquals(expected = url, actual = decodedUrl)
    }
}