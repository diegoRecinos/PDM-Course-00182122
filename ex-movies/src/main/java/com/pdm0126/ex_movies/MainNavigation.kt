package com.pdm0126.ex_movies

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.ex_movies.screens.moviedetail.MovieDetailScreenV2
import com.pdm0126.ex_movies.screens.movielist.MovieListScreen

@Composable
fun MovieApp() {
  val backStack = rememberNavBackStack(Routes.Home)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<Routes.Home> {
        MovieListScreen(
          navigateToDetail = { movieId ->
            backStack.add(Routes.MovieDetail(movieId))
          }
        )
      }
      entry<Routes.MovieDetail> { key ->
        MovieDetailScreenV2(
          movieId = key.movieId,
          navigateBack = {
            backStack.removeLastOrNull()
          }
        )
      }
    },
    transitionSpec = {
      slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(500)
      ) togetherWith slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(500)
      )
    },
    popTransitionSpec = {
      slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(500)
      ) togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(500)
      )
    },
    predictivePopTransitionSpec = {
      slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(250)
      ) togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(250)
      )
    }
  )

}