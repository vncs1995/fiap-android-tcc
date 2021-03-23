package br.com.borrowgame.data.remote.datasource

import android.util.Log
import br.com.borrowgame.R
import br.com.borrowgame.data.remote.mapper.GamesFirebaseMapper
import br.com.borrowgame.data.remote.mapper.NewGameFirebasePayloadMapper
import br.com.borrowgame.data.remote.mapper.NewUserFirebasePayloadMapper
import br.com.borrowgame.domain.entity.Address
import br.com.borrowgame.domain.entity.Game
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.UserGame
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class GamesRemoteDatasourceImpl (
    private val firestore: FirebaseFirestore,
    private val mAuth: FirebaseAuth
): GamesRemoteDatasource{
    override suspend fun registerGame(game: UserGame): RequestState<UserGame> {
        return try {
            val userId = mAuth.currentUser?.uid

            if (userId == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {
                val firebaseUser = firestore
                    .collection("users")
                    .document(userId)

                val newGameFirebasePayload = NewGameFirebasePayloadMapper.mapToNewUserGameFirebasePayload(
                    newGame = game,
                    address = Address("streetName", "number", "district", "state", "zipCode")
                )

                firebaseUser
                    .set(newGameFirebasePayload, SetOptions.merge())
                    .await()

                firestore
                    .collection("games")
                    .add(newGameFirebasePayload)
                    .await()

                RequestState.Success(NewGameFirebasePayloadMapper.mapToUserGame(newGameFirebasePayload))
            }
        } catch (e: java.lang.Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun getGames(): RequestState<MutableList<Game>> {
        return try {
            val userId = mAuth.currentUser?.uid
            if (userId == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {
                val user = firestore.collection("user")
                    .document(userId).get().await()

                val games = firestore
                    .collection("games")
    //                .whereEqualTo("state", user["state"])
                    .orderBy("name")
                    .get()
                    .await()
                if(games.isEmpty) {
                    RequestState.Error(Error(R.string.empty_list_error.toString()))
                } else {
                    Log.d("GETGAMES", "" + games.isEmpty + user.data)
                    RequestState.Success(GamesFirebaseMapper.mapToListOfGames(games.documents))
                }
            }
        } catch(e:java.lang.Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun deleteGame(game: Game): RequestState<Game> {
        TODO("Not yet implemented")
    }

    override suspend fun updateGame(game: Game): RequestState<Game> {
        TODO("Not yet implemented")
    }

}