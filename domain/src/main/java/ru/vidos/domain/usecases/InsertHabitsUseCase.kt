package ru.vidos.domain.usecases

import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.DataBaseRepository

class InsertHabitsUseCase  (
    private val dataBaseRepository: DataBaseRepository
) {
    suspend operator fun invoke(habitsList: List<Habit>) {
        dataBaseRepository.insertHabits(habitsList)
    }
}