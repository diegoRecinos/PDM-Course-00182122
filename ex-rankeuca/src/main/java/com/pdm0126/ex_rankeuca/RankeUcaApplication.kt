package com.pdm0126.ex_rankeuca

import android.app.Application
import com.pdm0126.ex_rankeuca.data.AppProvider

class RankeUcaApplication : Application() {
    val appProvider by lazy { AppProvider(this) }
}