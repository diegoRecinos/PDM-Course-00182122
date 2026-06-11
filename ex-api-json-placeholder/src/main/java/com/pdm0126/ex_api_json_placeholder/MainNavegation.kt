package com.pdm0126.ex_api_json_placeholder

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

import com.pdm0126.ex_api_json_placeholder.ui.screens.homescreen.HomeScreen
import com.pdm0126.ex_api_json_placeholder.ui.screens.screen2.Screen2
import com.pdm0126.ex_api_json_placeholder.ui.screens.screen1.Screen1
import java.util.Map.entry

@Composable
fun App(modifier: Modifier = Modifier){
    //creando el backstack
    //backstack needs to know the initial destination
    val backStack = rememberNavBackStack(Routes.HomeScreen)

    Scaffold(
        modifier = Modifier.fillMaxSize(),

    ) { innerPadding ->

        //navdisplay contenedor que renderiza las pantallas
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            //maneja boton de atras
            onBack = { backStack.removeLastOrNull()},

            entryProvider = entryProvider{
                //mapear cada objeto de routes.kt a un composable
                entry<Routes.HomeScreen>{
                    HomeScreen(
                        onNavigateToScreen1 = { backStack.add(Routes.Screen1) },
                        onNavigateToScreen2 = { backStack.add(Routes.Screen2) }
                    )
                }

                entry<Routes.Screen1>{
                    Screen1(onBack = { backStack.removeLastOrNull() })
                }

                entry<Routes.Screen2>{
                    Screen2(onBack = {backStack.removeLastOrNull()})
                }

            }

        )
    }

}