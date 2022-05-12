package ru.vidos.habitstracker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.vidos.habitstracker.models.Habit


class Converters {
    @TypeConverter
    fun listToJsonString(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

}