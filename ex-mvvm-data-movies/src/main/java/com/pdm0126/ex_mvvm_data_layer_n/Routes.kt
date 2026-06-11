package com.pdm0126.ex_mvvm_data_layer_n

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
  @Serializable
  data object Home : Routes()


  @Serializable
  data class MovieDetail(val movieId: Int) : Routes()
}
