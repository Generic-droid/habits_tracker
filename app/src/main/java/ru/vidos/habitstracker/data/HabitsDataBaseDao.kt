package ru.vidos.habitstracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.utils.HabitTypes

@Dao
interface HabitsDataBaseDao {

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * from habits_table")
    fun getHabits(): LiveData<List<Habit>>

    @Query("SELECT * from habits_table " +
            "WHERE habit_type = :type " +
            "AND habit_title LIKE '%' || :title || '%' " +
            "ORDER BY  habit_priority ASC")
    fun getHabitsSortedByAscending(type: HabitTypes, title: String?): LiveData<List<Habit>>

    @Query("SELECT * from habits_table " +
            "WHERE habit_type = :type " +
            "AND habit_title LIKE '%' || :title || '%' " +
            "ORDER BY  habit_priority ASC")
    fun getHabitsSortedByDescending(type: HabitTypes, title: String?): LiveData<List<Habit>>

    @Query("DELETE FROM habits_table")
    fun clear()
}