package com.example.harrypotterapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.harrypotterapp.ui.components.Loader
import com.example.harrypotterapp.ui.view.CharacterDetailScreen
import com.example.harrypotterapp.ui.view.HarryPotterCharactersListScreen
import com.example.harrypotterapp.ui.viewModel.HarryPotterViewModel
import com.example.utilities.ResultState

/*@Composable
fun AppNavigationGraph(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.CHARACTER_LIST){
        composable(Routes.CHARACTER_LIST){
            HarryPotterCharactersListScreen()
        }

        composable("${Routes.CHARACTER_DETAIL_SCREEN}/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            // Retrieve character by name and display
            CharacterDetailScreen(character = viewModel.getCharacterByName(name))
        }
        *//*composable(Routes.CHARACTER_DETAIL_SCREEN){
            CharacterDetailScreen(character = )
        }*//*
    }

}*/

@Composable
fun AppNavHost(navController: NavHostController) {
    val harryPotterViewModel: HarryPotterViewModel = hiltViewModel()
    NavHost(navController=navController, startDestination = "characterList") {
        composable("characterList") { HarryPotterCharactersListScreen(navController = navController, harryPotterViewModel) }
        composable(
            route = "characterDetail/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })) {
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")

            // Observe the StateFlow
            val resultState by harryPotterViewModel.harryPotterCharacters.collectAsState()

            when (resultState) {
                is ResultState.Loading  -> {
                    Loader()
                }
                is ResultState.Success -> {
                    // Safely get the list of characters
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

