package br.com.borrowgame.domain.usecases.user

import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin
import br.com.borrowgame.domain.repository.UserRepository
import java.lang.Exception

class LoginUseCase(
        private val userRepository: UserRepository
) {
    suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        if(userLogin.email.isBlank() || userLogin.password.isBlank()) {
            return RequestState.Error(Exception("Preencha os dados de login e senha"))
        }

        return userRepository.doLogin(userLogin)
    }
}