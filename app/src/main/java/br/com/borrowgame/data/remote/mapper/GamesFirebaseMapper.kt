package br.com.borrowgame.data.remote.mapper

import br.com.borrowgame.domain.entity.Address
import br.com.borrowgame.domain.entity.Game
import com.google.firebase.firestore.DocumentSnapshot

object GamesFirebaseMapper {
    fun mapToListOfGames(games: List<DocumentSnapshot>): MutableList<Game> {
        val listGames: MutableList<Game> = ArrayList()

            for (game in games) {
                listGames.add(Game(
                    name = game["name"].toString(),
                    address = Address(streetName = game["streetName"].toString(), state = game["state"].toString(), zipCode = game["zipCode"].toString(),district = game["district"].toString(),number = game["number"].toString()),
                    gameCondition = game["gameCondition"].toString(),
                    numberOfPlayers = game["numberOfPlayers"].toString().toInt()
                ))
            }

        return listGames;
    }
}