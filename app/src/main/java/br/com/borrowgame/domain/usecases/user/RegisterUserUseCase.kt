package br.com.borrowgame.domain.usecases.user

import br.com.borrowgame.domain.entity.RegisterUser
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.repository.UserRepository

class RegisterUserUseCase(
        private val userRepository: UserRepository
) {
    suspend fun registerUser(user: RegisterUser):RequestState<User> = userRepository.registerUser(user)
}