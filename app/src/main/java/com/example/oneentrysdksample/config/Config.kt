package com.example.oneentrysdksample.config

import android.content.Context
import android.util.Log
import com.example.oneentry.network.core.OneEntryCertificateCredential
import com.example.oneentry.network.core.OneEntryCore
import java.io.File

class Config {

    companion object {

        fun configure(context: Context) {

            try {

                val domain = "sdk-sample.oneentry.cloud"
                val fileName = "new_certificate.p12"
                val filePath = File(context.filesDir, fileName).absolutePath
                val password = "WASALWASAL123"

                val credential = OneEntryCertificateCredential(filePath, password)

                OneEntryCore.initializeApp(domain, credential)

            } catch (error: Exception) {

                Log.e("Error initialize app", error.toString())
            }
        }
    }
}