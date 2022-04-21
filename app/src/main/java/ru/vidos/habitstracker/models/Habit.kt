package ru.vidos.habitstracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "habits_table")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "habit_color")
    var color: String,

    @ColumnInfo(name = "habit_title")
    var title: String,

    @ColumnInfo(name = "habit_description")
    var description: String,

    @ColumnInfo(name = "habit_priority")
    var priority: Int,

    @ColumnInfo(name = "habit_type")
    var type: String,

    @ColumnInfo(name = "repeat_quantity")
    var quantity: String,

    @ColumnInfo(name = "repeat_periodicity")
    var periodicity: String
) : Serializable