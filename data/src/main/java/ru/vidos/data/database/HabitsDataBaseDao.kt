package ru.vidos.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.vidos.data.models.HabitDto

@Dao
interface HabitsDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habitDto: HabitDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitEntities: List<HabitDto>)

    @Update
    suspend fun update(habitDto: HabitDto)

    @Delete
    suspend fun delete(habitDto: HabitDto)

    @Query("SELECT * from habits_table " +
            "WHERE habit_type = :type " +
            "AND habit_title LIKE '%' || :title || '%' " +
            "ORDER BY " +
            "CASE WHEN :sortType = 1 THEN habit_priority END ASC, " +
            "CASE WHEN :sortType = 2 THEN habit_priority END DESC")
    fun getHabits(type: Int, title: String, sortType: Int): Flow<List<HabitDto>>

    @Query("DELETE FROM habits_table")
    suspend fun clear()
}