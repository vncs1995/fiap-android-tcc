package br.com.borrowgame.domain.entity

data class Address (
    val streetName: String,
    val number: Number,
    val district: String,
    val state: String,
    val zipCode: String
)