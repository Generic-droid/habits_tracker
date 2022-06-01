package ru.vidos.domain.usecases

import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.DataBaseRepository

class InsertHabitUseCase  (
    private val dataBaseRepository: DataBaseRepository
) {
    suspend operator fun invoke(habit: Habit) {
        dataBaseRepository.insertHabit(habit)
    }
}