package ru.vidos.habitstracker.domain

import dagger.Component
import ru.vidos.habitstracker.data.database.HabitsDataBaseDao
import ru.vidos.habitstracker.data.database.HabitsDatabase
import ru.vidos.habitstracker.data.network.HabitsApiService
import ru.vidos.habitstracker.domain.usecases.*
import javax.inject.Singleton

// Definition of the Application graph
@Singleton
@Component(modules = [HabitsModule::class])
interface ApplicationComponent {

    // The return type  of functions inside the component interface is
    // what can be provided from the container
    fun getGetHabitsUseCase(): GetHabitsUseCase

    fun getInsertHabitUseCase(): InsertHabitUseCase

    fun getUpdateHabitUseCase(): UpdateHabitUseCase

    fun getDeleteHabitUseCase(): DeleteHabitUseCase

    fun getHabitsDataBase(): HabitsDatabase

    fun getHabitsDataBaseDao(): HabitsDataBaseDao

    fun getFetchHabitsUseCase(): FetchHabitsUseCase

    fun getHabitsApi(): HabitsApiService.HabitsApi

}