package ru.vidos.habitstracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitTypes

@Dao
interface HabitsDataBaseDao {

    @Insert
    suspend fun insert(habit: Habit)

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Query("SELECT * from habits_table " +
            "WHERE habit_type = :type " +
            "AND habit_title LIKE '%' || :title || '%' " +
            "ORDER BY " +
            "CASE WHEN :sortType = 1 THEN habit_priority END ASC, " +
            "CASE WHEN :sortType = 2 THEN habit_priority END DESC")
    fun getHabits(type: HabitTypes, title: String, sortType: Int): LiveData<List<Habit>>

    @Query("DELETE FROM habits_table")
    suspend fun clear()
}