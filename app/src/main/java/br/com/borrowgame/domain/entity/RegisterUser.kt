package br.com.borrowgame.domain.entity

data class RegisterUser(
    val credentials: UserLogin,
    val name: String
    val surname: String
)