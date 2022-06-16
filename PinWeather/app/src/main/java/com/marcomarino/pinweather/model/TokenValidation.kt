package com.marcomarino.pinweather.model

data class TokenValidation(val data: SubTokenValidation)
data class SubTokenValidation (val validateToken: Boolean)