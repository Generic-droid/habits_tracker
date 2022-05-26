package ru.vidos.habitstracker.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.vidos.habitstracker.data.database.DataBaseRepository
import ru.vidos.habitstracker.domain.models.Habit
import javax.inject.Inject

class GetHabitsUseCase @Inject constructor (
    private val dataBaseRepository: DataBaseRepository
    ) {
    operator fun invoke(habitType: Int, title: String, sortType: Int): Flow<List<Habit>> {
        return dataBaseRepository.getHabits(habitType, title, sortType)
    }
}