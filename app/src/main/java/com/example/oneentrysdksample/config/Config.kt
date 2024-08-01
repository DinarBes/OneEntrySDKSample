package com.example.oneentrysdksample.config

import android.content.Context
import android.util.Log
import com.example.oneentry.network.core.OneEntryCertificateCredential
import com.example.oneentry.network.core.OneEntryCore
import com.example.oneentry.network.core.OneEntryTokenCredential
import java.io.File

class Config {

    companion object {

        fun configure(context: Context) {

            try {

                val domain = "sdk-sample.oneentry.cloud"
                val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiQW5kcm9pZCIsInNlcmlhbE51bWJlciI6MSwiaWF0IjoxNzE0MDYyOTYxLCJleHAiOjE3NDU1OTg5NTB9.FrTf1PdPH9IYksfzzv3jOfjX_rsdx-1K7-0Rl780mHc"
                val fileName = "new_certificate.p12"
                val filePath = File(context.filesDir, fileName).absolutePath
                val password = "WASALWASAL123"

//                val credential = OneEntryCertificateCredential(filePath, password)
                val credential = OneEntryTokenCredential(token)

                OneEntryCore.initializeApp(domain, credential)

            } catch (error: Exception) {

                Log.e("Error initialize app", error.toString())
            }
        }
    }
}