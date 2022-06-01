package ru.vidos.habitstracker.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vidos.data.models.HabitDto
import ru.vidos.domain.usecases.*
import ru.vidos.habitstracker.ui.HabitsTrackerViewModelFactory
import ru.vidos.habitstracker.ui.home.addedit.AddEditHabitViewModelFactory
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideAppContext(): Context {
        return app.applicationContext
    }


    @Provides
    @Singleton
    fun provideHabitsTrackerViewModelFactory(
        getHabitsUseCase: GetHabitsUseCase,
        deleteHabitUseCase: DeleteHabitUseCase,
        fetchHabitsUseCase: FetchHabitsUseCase,
        updateHabitCountUseCase: UpdateHabitCountUseCase
    ) : HabitsTrackerViewModelFactory {
        return HabitsTrackerViewModelFactory(
            getHabitsUseCase,
            deleteHabitUseCase,
            fetchHabitsUseCase,
            updateHabitCountUseCase
        )
    }

    @Provides
    @Singleton
    fun provideAddEditHabitViewModelFactory(
        updateHabitUseCase: UpdateHabitUseCase,
        putHabitUseCase: PutHabitUseCase,
        habitDto: HabitDto?
    ): AddEditHabitViewModelFactory{
        return AddEditHabitViewModelFactory(
            updateHabitUseCase,
            putHabitUseCase,
            habitDto
        )
    }
}