package com.apps.corona.tracker

import android.content.Context
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UnitTestSample {

    @Mock
    private lateinit var mockContext: Context

    @Test
    fun readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        val myObjectUnderTest = MainActivity()

        // ...when the string is returned from the object under test...
        val result = myObjectUnderTest.getCountryData()

        // ...then the result should be the expected one.
        assertThat(myObjectUnderTest.responseStats?.code(), `is`(200))
    }

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["x-rapidapi-host"] = "coronavirus-monitor.p.rapidapi.com"
        headerMap["x-rapidapi-key"] = "f93beb3a82msha42fbaf6bfe7e1cp1cbda6jsn9374b3300261"
        return headerMap
    }

}