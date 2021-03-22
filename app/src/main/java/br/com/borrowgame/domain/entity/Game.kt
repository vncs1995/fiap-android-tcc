package br.com.borrowgame.domain.entity

data class Game (
    val name: String,
    val numberOfPlayers: Number,
    val gameCondition: String,
    val address: Address
)