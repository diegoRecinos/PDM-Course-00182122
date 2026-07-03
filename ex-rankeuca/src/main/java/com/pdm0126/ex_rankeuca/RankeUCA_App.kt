package com.pdm0126.ex_rankeuca

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.ex_rankeuca.screens.home.HomeScreen
import com.pdm0126.ex_rankeuca.screens.home.HomeScreenViewModel
import com.pdm0126.ex_rankeuca.screens.resultscreen.ResultScreen
import com.pdm0126.ex_rankeuca.screens.resultscreen.ResultScreenViewModel
import com.pdm0126.ex_rankeuca.screens.options.OptionsScreen
import com.pdm0126.ex_rankeuca.screens.questions.QuestionsScreen
import com.pdm0126.ex_rankeuca.screens.menu.MenuScreen

@Composable
fun RankeUCA_App() {
  val backStack = rememberNavBackStack(Routes.Menu)
  val homeViewModel: HomeScreenViewModel = viewModel(
    factory = HomeScreenViewModel.Factory
  )
  val resultViewModel: ResultScreenViewModel = viewModel(
    factory = ResultScreenViewModel.Factory
  )

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<Routes.Menu> {
        MenuScreen(
          onNavigateToManager = {
            backStack.add(Routes.Questions)
          },
          onNavigateToVote = {
            backStack.add(Routes.Home)
          }
        )
      }
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
