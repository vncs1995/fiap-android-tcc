package br.com.borrowgame.data.remote.datasource

import br.com.borrowgame.domain.entity.RegisterUser
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin

interface UserRemoteDataSource {
    suspend fun getUserLogged() : RequestState<User>
    suspend fun doLogin(userLogin: UserLogin): RequestState<User>
    suspend fun registerUser(user: RegisterUser): RequestState<User>
    suspend fun logout(): RequestState<Boolean>
}