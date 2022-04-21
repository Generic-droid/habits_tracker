package ru.vidos.habitstracker

import androidx.lifecycle.LiveData
import ru.vidos.habitstracker.data.HabitsDataBaseDao
import ru.vidos.habitstracker.models.Habit

class HabitsTrackerRepository(private val habitsDataBaseDao: HabitsDataBaseDao) {


    fun getHabits(): LiveData<List<Habit>>{
        return habitsDataBaseDao.getHabits()
    }

    fun insertHabit(habit: Habit) {
        habitsDataBaseDao.insert(habit)
    }

    fun updateHabit(habit: Habit) {
        habitsDataBaseDao.update(habit)
    }

    fun deleteHabit(habit: Habit) {
        habitsDataBaseDao.delete(habit)
    }
}