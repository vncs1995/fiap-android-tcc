package br.com.borrowgame.ui.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.usecases.user.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel(
    val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {
    val userLoggedState = MutableLiveData<RequestState<User>>()

    fun getUserLogged() {
        viewModelScope.launch {
            userLoggedState.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}