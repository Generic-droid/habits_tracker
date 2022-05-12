package ru.vidos.habitstracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "habits_table")
@JsonClass(generateAdapter = true)
data class Habit(
    // @field:Json(ignore = true)
    // @PrimaryKey(autoGenerate = true)
    // var id: Int = 0,

    @Json(name = "color")
    @ColumnInfo(name = "habit_color")
    var color: Int,

    @Json(name = "count")
    @ColumnInfo(name = "habit_count")
    var count: Int,

    @Json(name = "date")
    @ColumnInfo(name = "habit_date")
    var date: Long,

    @Json(name = "description")
    @ColumnInfo(name = "habit_description")
    var description: String,

    @Json(name = "done_dates")
    @ColumnInfo(name = "done_dates_list")
    var dates: List<Int>,

    @Json(name = "frequency")
    @ColumnInfo(name = "repeat_frequency")
    var frequency: Int,

    @Json(name = "priority")
    @ColumnInfo(name = "habit_priority")
    var priority: Int,

    @Json(name = "title")
    @ColumnInfo(name = "habit_title")
    var title: String,

    @Json(name = "type")
    @ColumnInfo(name = "habit_type")
    var type: Int,

    @Json(name = "uid")
    @PrimaryKey
    var uid: String,

) : Serializable

enum class SortTypes {
    NONE, ASC, DESC
}

enum class HabitPriority {
    HIGH, MEDIUM, LOW
}

enum class HabitTypes(val type: String) {
    GOOD("good"), BAD("bad")
}