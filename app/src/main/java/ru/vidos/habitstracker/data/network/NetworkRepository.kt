package ru.vidos.habitstracker.data.network

import kotlinx.coroutines.delay
import ru.vidos.habitstracker.domain.models.Habit
import ru.vidos.habitstracker.domain.models.UserID
import javax.inject.Inject

// @Inject lets Dagger know how to create instances of this object
class NetworkRepository @Inject constructor(
    private val habitsApi: HabitsApiService.HabitsApi
) {
    /**
     * Fetch whole [List] of [Habit]s from remote server
     */
    suspend fun fetchHabits(): List<Habit> {
        delay(3000)
        return habitsApi.retrofitService.fetchHabits()
    }

    /**
     * Put [Habit] to remote server
     */
    suspend fun putHabit(habit: Habit) {
        delay(3000)
        val uid = habitsApi.retrofitService.putHabit(habit)
        habit.uid = uid.uid
    }

    /**
     * Update [Habit] on remote server
     */
    suspend fun updateHabit(habit: Habit) {
        delay(3000)
        habitsApi.retrofitService.putHabit(habit)
    }

    /**
     * Delete [Habit] from remote server
     */
    suspend fun deleteHabit(habit: Habit) {
        val uid = UserID(habit.uid)
        delay(3000)
        habitsApi.retrofitService.deleteHabit(uid)
    }
}