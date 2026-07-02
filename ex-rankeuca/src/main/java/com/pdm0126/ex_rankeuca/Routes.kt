package com.pdm0126.ex_rankeuca

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
  @Serializable
  data object Home : Routes()

  @Serializable
  data object ResultScreen : Routes()

  @Serializable
  data object Questions : Routes()

  @Serializable
  data class Options(val questionId: Int) : Routes()
}
