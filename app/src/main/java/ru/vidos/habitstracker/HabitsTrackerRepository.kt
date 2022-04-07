package ru.vidos.habitstracker

import ru.vidos.habitstracker.models.Habit

class HabitsTrackerRepository {

    private val habitsList = mutableListOf<Habit>()

    fun getHabits(): List<Habit>{
        return habitsList
    }

    fun addHabit(habit: Habit) {
        habitsList.add(habit)
    }

    fun updateHabit(habit: Habit) {

        habitsList[habitsList.indexOf(habit)] = habit
    }
}