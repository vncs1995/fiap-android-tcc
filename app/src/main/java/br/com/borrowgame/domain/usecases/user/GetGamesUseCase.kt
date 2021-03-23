package br.com.borrowgame.domain.usecases.user

import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.repository.GameRepository

class GetGamesUseCase (
    private val gameRepository: GameRepository
) {
    suspend fun getGames(): RequestState<MutableList<Game>> = gameRepository.getGames()
}