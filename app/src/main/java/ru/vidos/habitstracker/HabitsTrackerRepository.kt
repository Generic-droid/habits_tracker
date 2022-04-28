package ru.vidos.habitstracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import ru.vidos.habitstracker.data.HabitsDataBaseDao
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitTypes

class HabitsTrackerRepository(private val habitsDataBaseDao: HabitsDataBaseDao) {


    fun getHabits(habitType: HabitTypes, title: String, sortType: Int): LiveData<List<Habit>>{
        return habitsDataBaseDao.getHabits(habitType, title, sortType)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertHabit(habit: Habit) {
        habitsDataBaseDao.insert(habit)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateHabit(habit: Habit) {
        habitsDataBaseDao.update(habit)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread  suspend fun deleteHabit(habit: Habit) {
        habitsDataBaseDao.delete(habit)
    }
}