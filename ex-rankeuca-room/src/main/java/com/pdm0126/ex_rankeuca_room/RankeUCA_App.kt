package com.pdm0126.ex_rankeuca_room

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.ex_rankeuca_room.screens.home.HomeScreen
import com.pdm0126.ex_rankeuca_room.screens.home.HomeScreenViewModel
import com.pdm0126.ex_rankeuca_room.screens.resultscreen.ResultScreen
import com.pdm0126.ex_rankeuca_room.screens.resultscreen.ResultScreenViewModel
import com.pdm0126.ex_rankeuca_room.screens.options.OptionsScreen
import com.pdm0126.ex_rankeuca_room.screens.questions.QuestionsScreen

@Composable
fun RankeUCA_App() {
  val backStack = rememberNavBackStack(Routes.Home)
  val homeViewModel: HomeScreenViewModel = viewModel(
    factory = HomeScreenViewModel.Factory
  )
  val resultViewModel: ResultScreenViewModel = viewModel()

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<Routes.Home> {
        HomeScreen(
          viewModel = homeViewModel,
          onNavigateToResultScreen = {
            resultViewModel.fetchOptions()
            backStack.add(Routes.ResultScreen)
          },
          onNavigateToOptions = {
            backStack.add(Routes.Questions)
          }
        )
      }
      entry<Routes.ResultScreen> {
        ResultScreen(
          viewModel = resultViewModel,
          onBack = { backStack.removeLastOrNull() },
          onNewVote = {
            homeViewModel.resetLocalVote()
            backStack.removeLastOrNull()
          }
        )
      }
      entry<Routes.Questions> {
        QuestionsScreen(
          onQuestionClick = { id ->
            backStack.add(Routes.Options(questionId = id))
          },
          onBack = { backStack.removeLastOrNull() }
        )
      }
      entry<Routes.Options> { route ->
        OptionsScreen(
          questionId = route.questionId,
          onBack = { backStack.removeLastOrNull() }
        )
      }
    },
  )
}
