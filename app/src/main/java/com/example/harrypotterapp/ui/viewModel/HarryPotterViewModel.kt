package com.example.harrypotterapp.ui.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.harrypotterapp.model.repository.HarryPotterRepository
import com.example.utilities.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HarryPotterViewModel @Inject constructor(
    private val harryPotterRepository: HarryPotterRepository
) : ViewModel() {

    private val _harryPotterCharacters: MutableStateFlow<ResultState<List<HarryPotterCharacter>>> =
        MutableStateFlow(ResultState.Loading())
    val harryPotterCharacters: StateFlow<ResultState<List<HarryPotterCharacter>>> = _harryPotterCharacters

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        getHarryPotterAllCharacters()
    }


    private fun getHarryPotterAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            harryPotterRepository.getAllCharacters()
                .collectLatest {
                    _harryPotterCharacters.value = it
                }
        }

    }

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch(Dispatchers.IO) {
            harryPotterRepository.searchCharacters(query).collect {
                _harryPotterCharacters.value = it
            }
        }
    }
}