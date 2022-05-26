package ru.vidos.habitstracker.utils

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

const val USER_TOKEN = "8eae3a77-8547-4e12-84fd-358623e7dc39"

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        return response.request.newBuilder()
            .header("Authorization", USER_TOKEN)
            .build()
    }
}