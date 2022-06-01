package ru.vidos.domain.usecases

import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.DataBaseRepository
import ru.vidos.domain.repos.NetworkRepository

class DeleteHabitUseCase (
    private val networkRepository: NetworkRepository,
    private val dataBaseRepository: DataBaseRepository
) {
    suspend operator fun invoke(habit: Habit) {
        dataBaseRepository.deleteHabit(habit)
        networkRepository.deleteHabit(habit)
    }
}