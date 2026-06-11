package com.pdm0126.ex_api_json_placeholder.data.api

import android.R.attr.level
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.http.ContentType
import io.ktor.http.contentType
import com.pdm0126.ex_api_json_placeholder.BuildConfig

object KtorClient{

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    //private val BASE_URL = BuildConfig.BASE_URL
    val client = HttpClient(OkHttp){
        //configuracion de JSON
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        //logs para depuracion
        install(Logging){
            level = LogLevel.ALL
        }

        //configuracion por defecto
        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }
}