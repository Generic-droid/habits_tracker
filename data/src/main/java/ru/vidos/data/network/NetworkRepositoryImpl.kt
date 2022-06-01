package ru.vidos.data.network

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.*
import ru.vidos.data.models.HabitDto
import ru.vidos.data.models.UserID
import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.NetworkRepository
import ru.vidos.domain.usecases.InsertHabitUseCase
import ru.vidos.domain.usecases.InsertHabitsUseCase
import java.util.*
import javax.inject.Inject

// @Inject lets Dagger know how to create instances of this object
class NetworkRepositoryImpl @Inject constructor(
    private val habitsApi: HabitsApiService.HabitsApi,
    private val insertHabitUseCase: InsertHabitUseCase,
    private val insertHabitsUseCase: InsertHabitsUseCase,
    private val context: Context
) : NetworkRepository {

    /**
     * Fetch whole [List] of [Habit]s from remote server
     */
    override suspend fun fetchHabits() {
        // delay(3000)
        MainScope().launch {
            try {
                val habitsList = withContext(Dispatchers.IO) {
                    habitsApi.retrofitService.fetchHabits()
                }
                insertHabitsUseCase.invoke(HabitDto.HabitDtoMapper.fromDtoList(habitsList))

            } catch (e: Exception) {

            }
        }
    }

    /**
     * Put [Habit] to remote server
     */
    override suspend fun putHabit(habit: Habit) {
        MainScope().launch {
            HabitDto.HabitDtoMapper.mapToDto(habit).let {
                try {
                    it.date = Calendar.getInstance().time.time
                    withContext(Dispatchers.IO) {
                        it.uid = habitsApi.retrofitService.putHabit(it).uid
                    }
                    insertHabitUseCase.invoke(HabitDto.HabitDtoMapper.mapFromDto(it))

                    Toast.makeText(context, "Habit saved", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {

                }
            }
        }
    }

    /**
     * Update [Habit] on remote server
     */
    override suspend fun updateHabit(habit: Habit) {
        MainScope().launch {
            HabitDto.HabitDtoMapper.mapToDto(habit).let {
                try {
                    it.date = Calendar.getInstance().time.time
                    withContext(Dispatchers.IO) {
                        habitsApi.retrofitService.putHabit(it)
                    }
                    Toast.makeText(context, "Habit updated", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {

                }
            }
        }
    }

    /**
     * Delete [Habit] from remote server
     */
    override suspend fun deleteHabit(habit: Habit) {
        val uid = UserID(habit.uid)
        MainScope().launch {
            try {
                withContext(Dispatchers.IO) {
                    habitsApi.retrofitService.deleteHabit(uid)
                }
                Toast.makeText(context, "Habit deleted", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {

            }
        }
    }
}