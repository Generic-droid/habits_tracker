package ru.vidos.domain.usecases

import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.NetworkRepository

class PutHabitUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(habit: Habit) {
        networkRepository.putHabit(habit)
    }
}