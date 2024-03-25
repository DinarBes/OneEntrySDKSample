package com.example.oneentrysdksample

import com.example.oneentry.network.OneEntryProject
import com.example.oneentry.network.core.OneEntryCertificateCredential
import com.example.oneentry.network.core.OneEntryCore
import com.example.oneentry.network.core.OneEntryTokenCredential
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Before
    fun config() = runBlocking {

        val domain = "sdk-sample.oneentry.cloud"
        val filePath = "C:\\Users\\vip\\AndroidStudioProjects\\OneEntrySDKSample\\app\\src\\main\\java\\com\\example\\oneentrysdksample\\config\\new_certificate.p12"
        val password = "WASALWASAL123"
        val credential = OneEntryCertificateCredential(filePath, password)

        OneEntryCore.initializeApp(domain, credential)
    }

    @Test
    fun testActiveLocale() = runBlocking {

        val provider = OneEntryProject.instance
        val locale = provider.locales()

        println(locale)

        assertNotNull(locale)
    }
}