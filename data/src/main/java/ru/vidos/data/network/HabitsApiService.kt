package ru.vidos.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import ru.vidos.data.models.HabitDto
import ru.vidos.data.models.UserID
import ru.vidos.domain.models.Habit
import javax.inject.Inject

val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
val authenticator = TokenAuthenticator()
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .authenticator(authenticator)
    .build()

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    // Add okHttpClient
    .client(okHttpClient)
    // Add converter factory to build a web services API.
    // The converter tells Retrofit what to do with the data it gets back from the web service.
    // Retrofit has a ScalarsConverter that supports strings and other primitive types.
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    // Add the base URI for the web service using baseUrl() method.
    .baseUrl(EndPoints.BASE_URL)
    // Finally, call build() to create the Retrofit object.
    .build()

/**
 * A public interface that exposes the [fetchHabits] method
 */
interface HabitsApiService{

    /**
     * Returns a [List] of [Habit] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "habits" endpoint will be requested with the GET
     * HTTP method
     */
    @GET(EndPoints.HABITS_URL)
    suspend fun fetchHabits(): List<HabitDto>

    @PUT(EndPoints.HABITS_URL)
    suspend fun putHabit(@Body habitDto: HabitDto) : UserID

    @HTTP(method = "DELETE", path = EndPoints.HABITS_URL, hasBody = true)
    suspend fun deleteHabit(@Body uid: UserID)

    /**
     * A public Api object that exposes the lazy-initialized Retrofit service
     */
    class HabitsApi @Inject constructor() {
        // Make this lazy initialization, to make sure it is initialized at its first usage.
        val retrofitService: HabitsApiService by lazy {
            retrofit.create(HabitsApiService::class.java)
        }
    }
}

class TokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        return response.request.newBuilder()
            .header("Authorization", USER_TOKEN)
            .build()
    }

    companion object {
        private const val USER_TOKEN = "Use your token"
    }
}

internal class EndPoints {
    companion object {

        //Base URL
        const val BASE_URL = "https://droid-test-server.doubletapp.ru"

        //Habits URL
        const val HABITS_URL = "/api/habit"

    }
}