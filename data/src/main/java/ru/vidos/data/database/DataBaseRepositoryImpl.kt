package ru.vidos.data.database

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vidos.data.models.HabitDto
import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.DataBaseRepository
import javax.inject.Inject

// @Inject lets Dagger know how to create instances of this object
class DataBaseRepositoryImpl @Inject constructor(
    private val habitsDataBaseDao: HabitsDataBaseDao
) : DataBaseRepository {

    /**
     * Get mapped [Flow] of [Habit]s from local DB
     */
    override fun getHabits(habitType: Int, title: String, sortType: Int) : Flow<List<Habit>> {

        val habitEntitiesList = habitsDataBaseDao.getHabits(habitType, title, sortType)

        return habitEntitiesList.map {
            HabitDto.HabitDtoMapper.fromDtoList(it)
        }
    }


    /**
     * Insert mapped [Habit] into local DB
     */
    override suspend fun insertHabit(habit: Habit) {
        Log.d("Ok", "Database repo save habit: $habit")
        habitsDataBaseDao.insert(
            HabitDto.HabitDtoMapper.mapToDto(habit)
        )
    }

    /**
     * Insert mapped [List] of [Habit]s into local DB
     */
    override suspend fun insertHabits(habitsList: List<Habit>) {
        Log.d("Ok", "Database repo save habits")
        habitsDataBaseDao.insert(
            HabitDto.HabitDtoMapper.toDtoList(habitsList)
        )
    }

    /**
     * Update mapped [Habit] in local DB
     */
    override suspend fun updateHabit(habit: Habit) {
        habitsDataBaseDao.update(
            HabitDto.HabitDtoMapper.mapToDto(habit)
        )
    }

    /**
     * Delete mapped [Habit] from local DB
     */
    override suspend fun deleteHabit(habit: Habit) {
        habitsDataBaseDao.delete(
            HabitDto.HabitDtoMapper.mapToDto(habit)
        )
    }
}