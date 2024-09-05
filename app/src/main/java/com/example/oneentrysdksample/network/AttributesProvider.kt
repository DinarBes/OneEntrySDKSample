package com.example.oneentrysdksample.network

import com.example.oneentry.model.attributes.AttributeSet
import com.example.oneentry.network.AttributesService

class AttributesProvider private constructor() {

    companion object {

        private val attributes = AttributesService.instance

        suspend fun getAttribute(
            setMarker: String,
            attributeMarker: String,
            langCode: String
        ): AttributeSet {

            return attributes.attribute(
                setMarker = setMarker,
                attributeMarker = attributeMarker,
                langCode = langCode
            )
        }
    }
}