package ru.vidos.habitstracker.domain.usecases

import ru.vidos.habitstracker.data.database.DataBaseRepository
import ru.vidos.habitstracker.data.network.NetworkRepository
import ru.vidos.habitstracker.domain.models.Habit
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor (
    private val networkRepository: NetworkRepository,
    private val dataBaseRepository: DataBaseRepository
) {
    suspend operator fun invoke(habit: Habit) {
        networkRepository.updateHabit(habit)
        dataBaseRepository.updateHabit(habit)
    }
}