package ru.vidos.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.vidos.domain.models.Habit
import java.io.Serializable

@Entity(tableName = "habits_table")
@JsonClass(generateAdapter = true)
data class HabitDto(

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

) : Serializable {

    object HabitDtoMapper {

        fun mapFromDto(entity: HabitDto) = Habit(
                color = entity.color,
                count = entity.count,
                date =  entity.date,
                description = entity.description,
                dates = entity.dates,
                frequency = entity.frequency,
                priority = entity.priority,
                title = entity.title,
                type = entity.type,
                uid = entity.uid
            )

        fun mapToDto(domainModel: Habit) = HabitDto(
                color = domainModel.color,
                count = domainModel.count,
                date =  domainModel.date,
                description = domainModel.description,
                dates = domainModel.dates,
                frequency = domainModel.frequency,
                priority = domainModel.priority,
                title = domainModel.title,
                type = domainModel.type,
                uid = domainModel.uid
            )

        fun fromDtoList(initial: List<HabitDto>): List<Habit> {
            return initial.map { mapFromDto(it) }
        }

        fun toDtoList(initial: List<Habit>): List<HabitDto> {
            return initial.map { mapToDto(it) }
        }
    }
}

enum class SortTypes {
    NONE, ASC, DESC
}

enum class HabitPriority {
    HIGH, MEDIUM, LOW
}

enum class HabitTypes(val type: String) {
    GOOD("good"), BAD("bad")
}