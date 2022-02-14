package com.timkwali.tmdmovies.common.utils

import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object FileReader {
    @Throws(IOException::class)
    fun readStringFromFile(fileName: String?): String {
        return if (fileName == null) "" else try {
            val inputStream = InstrumentationRegistry.getInstrumentation()
                .targetContext.applicationContext
                .assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, StandardCharsets.UTF_8)
            reader.readLines().forEach {
                builder.append(it)
            }
            builder.toString()
        } catch (exception: IOException) {
            throw exception
        }
    }
}