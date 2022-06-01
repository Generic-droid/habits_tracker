package ru.vidos.habitstracker.di

import dagger.Component
import ru.vidos.domain.usecases.*
import javax.inject.Singleton

// Definition of the Application graph
@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface ApplicationComponent {

    // The return type  of functions inside the component interface is
    // what can be provided from the container
    fun getGetHabitsUseCase(): GetHabitsUseCase

    fun getUpdateHabitUseCase(): UpdateHabitUseCase

    fun getDeleteHabitUseCase(): DeleteHabitUseCase

    fun getFetchHabitsUseCase(): FetchHabitsUseCase

    fun getPutHabitUseCase(): PutHabitUseCase

    fun getUpdateHabitCountUseCase(): UpdateHabitCountUseCase
}