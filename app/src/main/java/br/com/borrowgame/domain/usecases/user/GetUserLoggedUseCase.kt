package br.com.borrowgame.domain.usecases.user

import br.com.borrowgame.domain.repository.UserRepository

class GetUserLoggedUseCase (
    private val userRepository: UserRepository
) {
    suspend fun getUserLogged() = userRepository.getUserLogged()
}