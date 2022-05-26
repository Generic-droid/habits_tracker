package ru.vidos.habitstracker.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.vidos.habitstracker.domain.models.Habit

@Dao
interface HabitsDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habits: List<Habit>)

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
    fun getHabits(type: Int, title: String, sortType: Int): Flow<List<Habit>>

    @Query("DELETE FROM habits_table")
    suspend fun clear()
}