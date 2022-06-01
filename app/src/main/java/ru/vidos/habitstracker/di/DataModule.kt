package ru.vidos.habitstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vidos.data.database.DataBaseRepositoryImpl
import ru.vidos.data.database.HabitsDataBaseDao
import ru.vidos.data.database.HabitsDatabase
import ru.vidos.data.network.HabitsApiService
import ru.vidos.data.network.NetworkRepositoryImpl
import ru.vidos.domain.repos.DataBaseRepository
import ru.vidos.domain.repos.NetworkRepository
import ru.vidos.domain.usecases.InsertHabitUseCase
import ru.vidos.domain.usecases.InsertHabitsUseCase
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideHabitsDataBase(context: Context): HabitsDatabase {
        return HabitsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideHabitsDataBaseDao(habitsDatabase: HabitsDatabase): HabitsDataBaseDao {
        return habitsDatabase.habitsDataBaseDao()
    }

    @Provides
    @Singleton
    fun provideHabitsApi(): HabitsApiService.HabitsApi {
        return HabitsApiService.HabitsApi()
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(
        habitsApi: HabitsApiService.HabitsApi,
        insertHabitUseCase: InsertHabitUseCase,
        insertHabitsUseCase: InsertHabitsUseCase,
        context: Context
    ): NetworkRepository {
        return NetworkRepositoryImpl(
            habitsApi,
            insertHabitUseCase,
            insertHabitsUseCase,
            context
        )
    }

    @Provides
    @Singleton
    fun provideDataBaseRepository(habitsDataBaseDao: HabitsDataBaseDao): DataBaseRepository {
        return DataBaseRepositoryImpl(habitsDataBaseDao)
    }
}