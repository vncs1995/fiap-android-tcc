package br.com.borrowgame.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.*
import br.com.borrowgame.domain.usecases.user.RegisterUserUseCase
import kotlinx.coroutines.launch

class SignUpViewModel (
        private val registerUserUseCase: RegisterUserUseCase
):ViewModel() {
    val newUserState = MutableLiveData<RequestState<User>>()

    fun registerUser(name: String,
                     surname: String,
                     email: String,
                     streetName: String,
                     number: String,
                     district: String,
                     state: String,
                     zipCode: String,
                     password: String) {

        val payload = RegisterUser(
                credentials = UserLogin(email, password),
                name = name,
                surname = surname,
                address = Address(streetName,
                        number,
                        district,
                        state,
                        zipCode)
        )

        viewModelScope.launch {
            newUserState.value = registerUserUseCase.registerUser(payload)
        }
    }
}