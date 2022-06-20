package com.marcomarino.pinweather.network

import com.marcomarino.pinweather.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * This interface represents a clean API to retrieve and post information
 * to the PINWeather Server.
 */
interface API {

    companion object {
        const val GRAPHQL_URL = "/graphql"
    }

    interface WeatherAPI {

        companion object {
            const val ALL_ENTRIES_URL = "$GRAPHQL_URL?query={allWeatherEntries(token:\"{token}\"){id,city,temp,time,status,max,min}}"
            const val QUERIED_ENTRIES_URL = "$GRAPHQL_URL?query={queriedEntries(token:\"{token}\",query:\"{query}\"){id,city,temp,time,status,max,min}}"
            const val ADD_FAV_ENTRY_URL = "$GRAPHQL_URL?query=mutation{addFavPlace(token:\"{token}\",id:\"{id}\")}"
            const val DEL_FAV_ENTRY_URL = "$GRAPHQL_URL?query=mutation{deleteFavPlace(token:\"{token}\",id:\"{id}\")}"
        }

        @GET
        suspend fun getWeatherList(@Url url: String): Response<WeatherList>

        @POST
        suspend fun postWeatherList(@Url url: String): Response<PostResponse>
    }

    interface AccountAPI {

        companion object {
            const val LOGIN_URL = "$GRAPHQL_URL?query=mutation{login(email:\"{email}\",password:\"{password}\"){id,fullName,favourites,posts,email,token}}"
            const val SIGNUP_URL = "$GRAPHQL_URL?query=mutation{registerUser(fullName:\"{fullName}\",email:\"{email}\",password:\"{password}\"){id,fullName,favourites,posts,email,token}}"
            const val VALIDATE_TOKEN_URL = "$GRAPHQL_URL?query={validateToken(token:\"{token}\")}"
        }

        @POST
        suspend fun access(@Url url: String): Response<AccountLoginToken>

        @GET
        suspend fun validateToken(@Url url: String): Response<TokenValidation>
    }
}
