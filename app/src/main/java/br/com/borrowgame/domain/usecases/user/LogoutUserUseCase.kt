package br.com.borrowgame.domain.usecases.user

import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.repository.UserRepository

class LogoutUserUseCase (
    private val userRepository: UserRepository
) {
    suspend fun logoutUser():RequestState<Boolean> = userRepository.logout()
}