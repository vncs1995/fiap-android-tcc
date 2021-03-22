package br.com.borrowgame.domain.usecases.game

import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.UserGame
import br.com.borrowgame.domain.repository.GameRepository

class RegisterGameUseCase (
    private val gameRepository: GameRepository
) {
    suspend fun registerGame(game: UserGame): RequestState<UserGame> = gameRepository.registerGame(game)
}