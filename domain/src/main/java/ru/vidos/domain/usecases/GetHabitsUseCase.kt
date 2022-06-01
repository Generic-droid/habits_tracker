package ru.vidos.domain.usecases

import ru.vidos.domain.repos.DataBaseRepository

class GetHabitsUseCase (
    private val dataBaseRepository: DataBaseRepository
    ) {
    operator fun invoke(habitType: Int, title: String, sortType: Int) =
        dataBaseRepository.getHabits(habitType, title, sortType)
}