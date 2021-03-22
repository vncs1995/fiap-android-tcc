package br.com.borrowgame.data.remote.mapper

import br.com.borrowgame.data.remote.models.NewGameFirebasePayload
import br.com.borrowgame.domain.entity.Address
import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.UserGame

object NewGameFirebasePayloadMapper {
    fun mapToNewGameFirebasePayload (newGame: Game) = NewGameFirebasePayload(
        name = newGame.name,
        numberOfPlayers = newGame.numberOfPlayers,
        gameCondition = newGame.gameCondition,
        streetName = newGame.address.streetName,
        number = newGame.address.number,
        district = newGame.address.district,
        state = newGame.address.state,
        zipCode = newGame.address.zipCode
    )

    fun mapToNewUserGameFirebasePayload (newGame: UserGame, address: Address) = NewGameFirebasePayload(
        name = newGame.name,
        numberOfPlayers = newGame.numberOfPlayers,
        gameCondition = newGame.gameCondition,
        streetName = address.streetName,
        number = address.number,
        district = address.district,
        state = address.state,
        zipCode = address.zipCode
    )

    fun mapToGame (game: NewGameFirebasePayload) = Game(
        name = game.name,
        numberOfPlayers = game.numberOfPlayers,
        gameCondition = game.gameCondition,
        address = Address(
            streetName = game.streetName,
            number = game.number,
            district = game.district,
            state = game.state,
            zipCode = game.zipCode
        )
    )

    fun mapToUserGame (game: NewGameFirebasePayload) = UserGame(
        name = game.name,
        numberOfPlayers = game.numberOfPlayers,
        gameCondition = game.gameCondition
    )
}