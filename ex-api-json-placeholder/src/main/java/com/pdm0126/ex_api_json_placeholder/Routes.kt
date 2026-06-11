package com.pdm0126.ex_api_json_placeholder

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// In Nav3, each destination is an object or a data class
//Serializable convertir esa clase en una ruta de texto y manejar los argumentos de forma segura

@Serializable
//navkey is the key to identify the destination
sealed interface Routes : NavKey {

    @Serializable
    data object HomeScreen: Routes{

    }

    @Serializable
    data object Screen1: Routes{

    }

    @Serializable
    data object Screen2: Routes{

    }

    //nested
    data class SubScreen1(val id: Int): Routes{}

}