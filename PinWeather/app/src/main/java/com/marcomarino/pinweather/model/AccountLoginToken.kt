package com.marcomarino.pinweather.model

data class AccountLoginToken(
    val data: Token
)

data class Token(
    val login: User,
    val registerUser: User
)

data class User(
    val id: String,
    val favourites: List<Int>,
    val posts: List<Int>,
    val fullName: String,
    val email: String,
    val token: String
)