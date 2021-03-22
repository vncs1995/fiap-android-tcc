package br.com.borrowgame.ui.registergame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.UserGame
import br.com.borrowgame.domain.usecases.game.RegisterGameUseCase
import kotlinx.coroutines.launch

class RegisterGameViewModel (
    private val registerGameUseCase: RegisterGameUseCase
):ViewModel() {
    val newGameState = MutableLiveData<RequestState<UserGame>>()

    fun registerGame(
        name: String,
        numberOfPlayers: Number,
        gameCondition: String
    ) {
        val payload = UserGame(
            name,
            numberOfPlayers,
            gameCondition
        )

        viewModelScope.launch {
            newGameState.value = registerGameUseCase.registerGame(payload)
        }
    }
}