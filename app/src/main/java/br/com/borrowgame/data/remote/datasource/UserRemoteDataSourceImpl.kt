package br.com.borrowgame.data.remote.datasource

import android.util.Log
import br.com.borrowgame.R
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class UserRemoteDataSourceImpl(
        private val mAuth: FirebaseAuth
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
}