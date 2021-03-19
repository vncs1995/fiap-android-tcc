package br.com.borrowgame.domain.entity

data class Address (
    val streetName: String,
    val number: String,
    val district: String,
    val state: String,
    val zipCode: String
)