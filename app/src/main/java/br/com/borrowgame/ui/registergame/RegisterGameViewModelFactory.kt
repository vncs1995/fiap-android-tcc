package br.com.borrowgame.ui.registergame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.borrowgame.domain.usecases.game.RegisterGameUseCase

class RegisterGameViewModelFactory (
    private val registerGameUseCase: RegisterGameUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RegisterGameUseCase::class.java)
            .newInstance(registerGameUseCase)
    }
}