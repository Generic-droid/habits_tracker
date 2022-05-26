package ru.vidos.habitstracker.domain.usecases

import ru.vidos.habitstracker.data.database.DataBaseRepository
import ru.vidos.habitstracker.data.network.NetworkRepository
import javax.inject.Inject

class FetchHabitsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataBaseRepository: DataBaseRepository){

    suspend operator fun invoke() {
        val habitsList = networkRepository.fetchHabits()
        dataBaseRepository.insertHabits(habitsList)
    }
}