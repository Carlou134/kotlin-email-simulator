package user.domain

import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    private var password: String
){
    fun authenticate(provided: String) = this.password == provided
}

