package com.pdm0126.ex_rankeuca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pdm0126.ex_rankeuca.ui.theme.BasicTemplateTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      BasicTemplateTheme {
          RankeUCA_App()
      }
    }
  }
}