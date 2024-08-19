package com.example.libsproject

import nl.adaptivity.xmlutil.serialization.ElementSerializer
import nl.adaptivity.xmlutil.serialization.XML

class XmlExample {
    private val xmlParser = XML { autoPolymorphic = true }

    fun parseResponse(xml: String) {
        val deserialized = xmlParser.decodeFromString(ElementSerializer, xml)
        deserialized.childNodes.forEachBfsIndexed { node ->

        }
    }
}