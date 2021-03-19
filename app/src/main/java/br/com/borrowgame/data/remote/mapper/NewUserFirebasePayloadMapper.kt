package br.com.borrowgame.data.remote.mapper

import br.com.borrowgame.data.remote.models.NewUserFirebasePayload
import br.com.borrowgame.domain.entity.Address
import br.com.borrowgame.domain.entity.RegisterUser
import br.com.borrowgame.domain.entity.User
import br.com.borrowgame.domain.entity.UserLogin

object NewUserFirebasePayloadMapper {
    fun mapToNewUser (newUserFirebasePayload: NewUserFirebasePayload ) = RegisterUser(
        name = newUserFirebasePayload.name ?: "",
        credentials = UserLogin(email = newUserFirebasePayload.email ?: "", password = newUserFirebasePayload. password ?: ""),
        surname = newUserFirebasePayload.surname ?: "",
        address = Address(newUserFirebasePayload.streetName, newUserFirebasePayload.number, newUserFirebasePayload.district, newUserFirebasePayload.state, newUserFirebasePayload.zipCode)
    )

    fun mapToNewUserFirebasePayload (newUser: RegisterUser) = NewUserFirebasePayload(
        name = newUser.name,
        surname = newUser.surname,
        email = newUser.credentials.email,
        password = newUser.credentials.password,
        streetName= newUser.address.streetName,
        number= newUser.address.number,
        district= newUser.address.district,
        state= newUser.address.state,
        zipCode= newUser.address.zipCode
    )

    fun mapToUser(newUserFirebasePayload: NewUserFirebasePayload ) = User(
        name = newUserFirebasePayload. name ?: ""
    )
}