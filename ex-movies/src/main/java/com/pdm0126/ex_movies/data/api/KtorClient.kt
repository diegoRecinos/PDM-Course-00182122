package com.pdm0126.ex_movies.data.api

import android.util.Log
import com.pdm0126.ex_movies.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_TOKEN_TMDB = BuildConfig.API_TOKEN

    val client = HttpClient(OkHttp) {

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) { Log.d("KtorClient", message) }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            url(BASE_URL)
            header(HttpHeaders.Accept, "application/json")
            header(HttpHeaders.Authorization, "Bearer $API_TOKEN_TMDB")
        }
    }
}