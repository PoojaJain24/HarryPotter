package com.example.harrypotterapp.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.harrypotterapp.R
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.harrypotterapp.ui.components.Loader
import com.example.harrypotterapp.ui.viewModel.HarryPotterViewModel
import com.example.utilities.ResultState

const val TAG = "HarryPotterCharactersList"

@Composable
fun HarryPotterCharactersListScreen(
    navController: NavController,
    harryPotterViewModel: HarryPotterViewModel
) {
    val harryPotterCharacters by harryPotterViewModel.harryPotterCharacters.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {

        when (harryPotterCharacters) {
            is ResultState.Loading -> {
                Loader()
            }

            is ResultState.Success -> {
                Log.d(TAG, "Inside_Success")
                val characters: List<HarryPotterCharacter> =
                    (harryPotterCharacters as ResultState.Success<List<HarryPotterCharacter>>).data
                CharacterList(characters, harryPotterViewModel, navController)
            }

            is ResultState.Error -> {
                Log.d(TAG, "Inside_Error ${(harryPotterCharacters as ResultState.Error).error}")
            }
        }

    }

}

@Composable
fun CharacterList(
    characters: List<HarryPotterCharacter>,
    harryPotterViewModel: HarryPotterViewModel,
    navController: NavController,
) {

    val searchQuery by harryPotterViewModel.searchQuery.collectAsState()

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { harryPotterViewModel.search(it) },
            placeholder = { Text("Search by name or actor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn {
            items(characters) { character ->
                CharacterRow(character) {
                    navController.navigate("characterDetail/${character.name}")
                }
            }
        }

    }
}

@Composable
fun CharacterRow(character: HarryPotterCharacter, onClick: () -> Unit) {
    val houseColor = when (character.house) {
        "Gryffindor" -> colorResource(id = R.color.griffindor_house_color)
        "Slytherin" -> colorResource(id = R.color.slytherin_house_color)
        "Ravenclaw" -> colorResource(id = R.color.ravenclaw_house_color)
        "Hufflepuff" -> colorResource(id = R.color.hufflepuff_house_color)
        else -> colorResource(id = R.color.no_house_color)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(houseColor.copy(alpha = 0.3f))
            .clickable { onClick()}
            .padding(8.dp)
    ) {
        Column {
            Text(text = character.name, fontWeight = FontWeight.Bold)
            Text(text = "Actor: ${character.actor}")
            Text(text = "Species: ${character.species}")
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(houseColor)
        )
    }
}