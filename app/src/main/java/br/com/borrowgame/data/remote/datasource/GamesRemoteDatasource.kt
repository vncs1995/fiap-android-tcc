package br.com.borrowgame.data.remote.datasource

import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.UserGame

interface GamesRemoteDatasource {
    suspend fun registerGame(game: UserGame) : RequestState<UserGame>
    suspend fun getGames() : RequestState<MutableList<Game>>
    suspend fun deleteGame(game: Game) : RequestState<Game>
    suspend fun updateGame(game: Game) : RequestState<Game>
}