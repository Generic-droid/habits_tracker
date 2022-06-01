package ru.vidos.domain.repos

import ru.vidos.domain.models.Habit

interface NetworkRepository {

    suspend fun fetchHabits()

    suspend fun putHabit(habit: Habit)

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)
}