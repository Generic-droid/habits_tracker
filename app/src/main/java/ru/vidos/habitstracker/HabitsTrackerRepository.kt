package ru.vidos.habitstracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.delay
import ru.vidos.habitstracker.data.HabitsDataBaseDao
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.UserID
import ru.vidos.habitstracker.network.HabitsApiService

class HabitsTrackerRepository(
    private val habitsDataBaseDao: HabitsDataBaseDao,
    private val habitsApi: HabitsApiService.HabitsApi
) {

    /**
     * Get Habits from local DB
     */
    fun getHabits(habitType: Int, title: String, sortType: Int): LiveData<List<Habit>>{
        return habitsDataBaseDao.getHabits(habitType, title, sortType)
    }

    /**
     * Fetch whole List of Habits from remote server
     * & save it to local DB
     */
    suspend fun fetchHabits() {
        delay(3000)
        val habitsList = habitsApi.retrofitService.fetchHabits()
        habitsDataBaseDao.insert(habitsList)
    }

    /**
     *
     */
    suspend fun insertHabit(habit: Habit) {
        val uid = habitsApi.retrofitService.putHabit(habit)
        habit.uid = uid.uid
        habitsDataBaseDao.insert(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitsApi.retrofitService.putHabit(habit)
        habitsDataBaseDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        val uid = UserID(habit.uid)
        habitsApi.retrofitService.deleteHabit(uid)
        habitsDataBaseDao.delete(habit)
    }
}