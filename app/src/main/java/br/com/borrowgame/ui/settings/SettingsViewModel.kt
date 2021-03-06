package br.com.borrowgame.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.LogoutUserUseCase
import kotlinx.coroutines.launch

class SettingsViewModel (
    private val logoutUserUseCase: LogoutUserUseCase
): ViewModel() {
    val logoutUserState = MutableLiveData<RequestState<Boolean>>()

    fun logoutUser() {
        viewModelScope.launch {
            logoutUserState.value = logoutUserUseCase.logoutUser()
        }
    }
}