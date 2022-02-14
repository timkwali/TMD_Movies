package com.timkwali.tmdmovies.common.utils

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpProvider {
    // Timeout for the network requests
    private const val REQUEST_TIMEOUT = 3L

    var okHttpClient: OkHttpClient? = null
        get() = if (field == null) {
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build()
            field = okHttpClient
            okHttpClient
        } else {
            field
        }
        private set
}