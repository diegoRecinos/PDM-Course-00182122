package com.pdm0126.ex_movies

import android.app.Application
import com.pdm0126.ex_movies.data.AppProvider

class MoviesAppProviderApp : Application(){

    val appProvider by lazy { AppProvider(this) }

}
