package br.com.borrowgame.data.repository

import br.com.borrowgame.data.remote.datasource.UserRemoteDataSource
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
}