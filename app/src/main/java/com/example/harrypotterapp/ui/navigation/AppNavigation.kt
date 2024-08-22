package com.example.harrypotterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.harrypotterapp.ui.components.Loader
import com.example.harrypotterapp.ui.view.CharacterDetailScreen
import com.example.harrypotterapp.ui.view.HarryPotterCharactersListScreen
import com.example.harrypotterapp.ui.viewModel.HarryPotterViewModel
import com.example.utilities.ResultState

@Composable
fun AppNavHost(navController: NavHostController) {
    val harryPotterViewModel: HarryPotterViewModel = hiltViewModel()
    NavHost(navController=navController, startDestination = "characterList") {
        composable(Routes.CHARACTER_LIST) { HarryPotterCharactersListScreen(navController = navController, harryPotterViewModel) }
        composable(
            route = "${Routes.CHARACTER_DETAIL_SCREEN}/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })) {
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val resultState by harryPotterViewModel.harryPotterCharacters.collectAsState()

            when (resultState) {
                is ResultState.Loading  -> {
                    Loader()
                }
                is ResultState.Success -> {
                    val characters = (resultState as ResultState.Success).data
                    val character = characters.find { it.name == name }
                    character?.let {
                        CharacterDetailScreen(character = it)
                    }
                }
                is ResultState.Error -> {
                    // Handle error state if needed
                }
            }

        }
    }
}

