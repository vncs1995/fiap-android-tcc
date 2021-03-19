package br.com.borrowgame.data.repository

import br.com.borrowgame.data.remote.datasource.UserRemoteDataSource
import br.com.borrowgame.domain.entity.RegisterUser
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin
import br.com.borrowgame.domain.repository.UserRepository

class UserRepositoryImpl(
    val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return userRemoteDataSource.doLogin(userLogin)
    }

    override suspend fun registerUser(user: RegisterUser): RequestState<User> {
        return userRemoteDataSource.registerUser(user)
    }

    override suspend fun logout(): RequestState<Boolean> {
        return userRemoteDataSource.logout()
    }


}