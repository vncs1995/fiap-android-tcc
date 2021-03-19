package br.com.borrowgame.data.remote.models

import com.google.firebase.firestore.Exclude

data class NewUserFirebasePayload(
    val name: String? = null,
    val email: String? = null,
    @Exclude val password: String? = null,
    val surname: String? = null,
    val streetName: String,
    val number: String,
    val district: String,
    val state: String,
    val zipCode: String
)