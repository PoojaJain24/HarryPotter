package com.example.harrypotterapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.utilities.DateUtil

@Composable
fun CharacterDetailScreen(character: HarryPotterCharacter) {
    Column(modifier = Modifier.padding(16.dp)) {
        character.image?.let {
            Image(painter = rememberImagePainter(it), contentDescription = null, modifier = Modifier.size(300.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = character.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(text = "Actor: ${character.actor}")
        Text(text = "Species: ${character.species}")
        Text(text = "Date of Birth: ${character.dateOfBirth?.let {DateUtil.formatDateDDMMMYYYY(it) } ?: "Unknown"}")
        Text(text = "Status: ${if (character.alive) "Alive" else "Deceased"}")

    }
}









