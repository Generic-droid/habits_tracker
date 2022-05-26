package ru.vidos.habitstracker.data.database

import kotlinx.coroutines.flow.Flow
import ru.vidos.habitstracker.domain.models.Habit
import javax.inject.Inject

// @Inject lets Dagger know how to create instances of this object
class DataBaseRepository @Inject constructor(
    private val habitsDataBaseDao: HabitsDataBaseDao
) {

    /**
     * Get [Habit]s from local DB
     */
    fun getHabits(habitType: Int, title: String, sortType: Int): Flow<List<Habit>> {
        return habitsDataBaseDao.getHabits(habitType, title, sortType)
    }

    /**
     * Insert [Habit] into local DB
     */
    suspend fun insertHabit(habit: Habit) {
        habitsDataBaseDao.insert(habit)
    }

    /**
     * Insert [List] of [Habit]s into local DB
     */
    suspend fun insertHabits(habitsList: List<Habit>) {
        habitsDataBaseDao.insert(habitsList)
    }

    /**
     * Update [Habit] in local DB
     */
    suspend fun updateHabit(habit: Habit) {
        habitsDataBaseDao.update(habit)
    }

    /**
     * Delete [Habit] from local DB
     */
    suspend fun deleteHabit(habit: Habit) {
        habitsDataBaseDao.delete(habit)
    }
}