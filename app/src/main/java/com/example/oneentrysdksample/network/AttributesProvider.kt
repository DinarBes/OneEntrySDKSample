package com.example.oneentrysdksample.network

import com.example.oneentry.model.attributes.OneEntryAttributeSet
import com.example.oneentry.network.AttributesService

class AttributesProvider private constructor() {

    companion object {

        private val attributes = AttributesService.instance

        suspend fun getAttribute(
            setMarker: String,
            attributeMarker: String,
            langCode: String
        ): OneEntryAttributeSet {

            return attributes.attribute(
                setMarker = setMarker,
                attributeMarker = attributeMarker,
                langCode = langCode
            )
        }
    }
}