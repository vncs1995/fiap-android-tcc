package br.com.borrowgame.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.GetGamesUseCase
import kotlinx.coroutines.launch

class HomeViewModel (
    private val getGamesUseCase: GetGamesUseCase
): ViewModel() {
    val gamesState = MutableLiveData<RequestState<MutableList<Game>>>()

    fun getGames() {
        viewModelScope.launch {
            gamesState.value = getGamesUseCase.getGames()
        }
    }
}