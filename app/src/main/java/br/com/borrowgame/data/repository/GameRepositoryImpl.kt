package br.com.borrowgame.data.repository

import br.com.borrowgame.data.remote.datasource.GamesRemoteDatasource
import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.UserGame
import br.com.borrowgame.domain.repository.GameRepository

class GameRepositoryImpl (
    val gamesRemoteDatasource: GamesRemoteDatasource
):GameRepository {
    override suspend fun registerGame(game: UserGame): RequestState<UserGame> {
        return gamesRemoteDatasource.registerGame(game)
    }

    override suspend fun getGames(game: Game): RequestState<Game> {
        return gamesRemoteDatasource.getGames(game)
    }

    override suspend fun deleteGame(game: Game): RequestState<Game> {
        return gamesRemoteDatasource.deleteGame(game)
    }

    override suspend fun updateGame(game: Game): RequestState<Game> {
        return updateGame(game)
    }

}