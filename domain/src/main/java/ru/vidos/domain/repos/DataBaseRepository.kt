package ru.vidos.domain.repos

import kotlinx.coroutines.flow.Flow
import ru.vidos.domain.models.Habit

interface DataBaseRepository {
    fun getHabits(habitType: Int, title: String, sortType: Int) : Flow<List<Habit>>

    suspend fun insertHabits(habitsList: List<Habit>)

    suspend fun insertHabit(habit: Habit)

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)
}