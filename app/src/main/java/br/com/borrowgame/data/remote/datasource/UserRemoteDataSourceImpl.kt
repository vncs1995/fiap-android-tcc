package br.com.borrowgame.data.remote.datasource

import android.util.Log
import br.com.borrowgame.R
import br.com.borrowgame.data.remote.mapper.NewUserFirebasePayloadMapper
import br.com.borrowgame.domain.entity.RegisterUser
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl(
        private val mAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore
): UserRemoteDataSource {
    override suspend fun getUserLogged(): RequestState<User> {
        mAuth.currentUser?.reload()

       val firebaseUser = mAuth.currentUser
    Log.d("FIREBASE", "Has User?"+firebaseUser?.displayName)
        return if(firebaseUser == null) {
            RequestState.Error(Exception(R.string.label_user_not_logged.toString()))
        } else {
            RequestState.Success(User(firebaseUser.displayName ?: ""))
        }
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {

       return  try {
            mAuth.signInWithEmailAndPassword(userLogin.email, userLogin.password).await()
            val firebaseUser = mAuth.currentUser

            if(firebaseUser == null) {
                RequestState.Error(Exception(R.string.label_user_not_logged.toString()))
            } else {
                RequestState.Success(User(firebaseUser.displayName ?: ""))
            }
        }catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun registerUser(user: RegisterUser): RequestState<User> {
        return try {
            mAuth.createUserWithEmailAndPassword(user.credentials.email, user.credentials.password).await()
            val newUserFirebasePayload =
                NewUserFirebasePayloadMapper.mapToNewUserFirebasePayload(user)
            val userId = mAuth.currentUser?.uid
            if (userId == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {
                firestore
                    .collection("users")
                    .document(userId)
                    .set(newUserFirebasePayload, SetOptions.merge())
                    .await()
                RequestState.Success(NewUserFirebasePayloadMapper.mapToUser(newUserFirebasePayload))
            }
        } catch (e:java.lang.Exception){
            RequestState.Error(e)
        }
    }

    override suspend fun logout(): RequestState<Boolean> {
        return try {
            mAuth.signOut()
            mAuth.currentUser?.reload()
            RequestState.Success(true)
        } catch (e:java.lang.Exception){
            RequestState.Error(e)
        }
    }

}