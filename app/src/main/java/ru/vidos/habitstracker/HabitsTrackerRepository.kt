package ru.vidos.habitstracker

import ru.vidos.habitstracker.data.Habit

class HabitsTrackerRepository {

    private val habitsList = mutableListOf<Habit>()

    fun getHabits(): List<Habit>{
        return habitsList
    }

    fun addHabit(habit: Habit) {
        habitsList.add(habit)
    }
}