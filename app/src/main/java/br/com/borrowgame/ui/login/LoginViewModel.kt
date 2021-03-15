package br.com.borrowgame.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin
import br.com.borrowgame.domain.usecases.user.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
        private val loginUseCase: LoginUseCase
) : ViewModel() {
    val loginState = MutableLiveData<RequestState<User>>()

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }
}