package br.com.borrowgame.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.borrowgame.domain.usecases.user.RegisterUserUseCase

class SignUpViewModelFactory (
        private val registerUserUseCase: RegisterUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RegisterUserUseCase::class.java)
                .newInstance(registerUserUseCase)
    }
}