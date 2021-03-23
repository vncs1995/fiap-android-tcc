package br.com.borrowgame.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.borrowgame.domain.usecases.user.GetGamesUseCase
import br.com.borrowgame.domain.usecases.user.LogoutUserUseCase

class HomeViewModelFactory (
    private val getGamesUseCase: GetGamesUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetGamesUseCase::class.java)
            .newInstance(getGamesUseCase)
    }
}