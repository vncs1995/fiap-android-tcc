package br.com.borrowgame.domain.repository

import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin

interface UserRepository {
    suspend fun getUserLogged(): RequestState<User>
    suspend fun doLogin(userLogin: UserLogin): RequestState<User>
}