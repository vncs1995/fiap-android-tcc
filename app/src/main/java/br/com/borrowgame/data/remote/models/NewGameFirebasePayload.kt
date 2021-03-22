package br.com.borrowgame.data.remote.models

data class NewGameFirebasePayload(
    val name: String,
    val numberOfPlayers: Number,
    val gameCondition: String,
    val streetName: String,
    val number: String,
    val district: String,
    val state: String,
    val zipCode: String
)