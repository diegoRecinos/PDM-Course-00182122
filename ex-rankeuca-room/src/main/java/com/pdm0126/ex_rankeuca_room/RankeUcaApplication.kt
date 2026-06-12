package com.pdm0126.ex_rankeuca_room

import android.app.Application
import com.pdm0126.ex_rankeuca_room.data.AppProvider

class RankeUcaApplication : Application() {
    val appProvider by lazy { AppProvider(this) }
}