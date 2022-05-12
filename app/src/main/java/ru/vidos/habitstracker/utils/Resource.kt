package ru.vidos.habitstracker.utils

/**
 * This is used for getting states of network call
 */

data class Resource <out T>(val habitsApiStatus: HabitsApiStatus, val data: T?, val message: String?) {

    enum class HabitsApiStatus {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T>{
            return Resource(HabitsApiStatus.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T>{
            return Resource(HabitsApiStatus.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T>{
            return Resource(HabitsApiStatus.LOADING, data, null)
        }
    }
}