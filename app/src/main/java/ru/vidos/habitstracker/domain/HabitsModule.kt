package ru.vidos.habitstracker.domain

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.vidos.habitstracker.data.database.DataBaseRepository
import ru.vidos.habitstracker.data.database.HabitsDataBaseDao
import ru.vidos.habitstracker.data.database.HabitsDatabase
import ru.vidos.habitstracker.data.network.HabitsApiService
import ru.vidos.habitstracker.data.network.NetworkRepository
import ru.vidos.habitstracker.domain.usecases.*
import javax.inject.Singleton


@Module
class HabitsModule(val app: Application) {

    @Provides
    fun provideGetHabitsUseCase(dataBaseRepository: DataBaseRepository): GetHabitsUseCase {
        return GetHabitsUseCase(dataBaseRepository)
    }

    @Provides
    fun provideInsertHabitUseCase(
        networkRepository: NetworkRepository,
        dataBaseRepository: DataBaseRepository): InsertHabitUseCase {
        return InsertHabitUseCase(networkRepository,dataBaseRepository)
    }

    @Provides
    fun provideUpdateHabitUseCase(
        networkRepository: NetworkRepository,
        dataBaseRepository: DataBaseRepository): UpdateHabitUseCase {
        return UpdateHabitUseCase(networkRepository, dataBaseRepository)
    }

    @Provides
    fun provideDeleteHabitUseCase(
        networkRepository: NetworkRepository,
        dataBaseRepository: DataBaseRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(networkRepository,dataBaseRepository)
    }

    @Provides
    fun provideGetFetchHabitsUseCase(
        networkRepository: NetworkRepository,
        dataBaseRepository: DataBaseRepository
    ) : FetchHabitsUseCase {
        return FetchHabitsUseCase(networkRepository, dataBaseRepository)
    }

    @Provides
    @Singleton
    fun provideHabitsDataBase(): HabitsDatabase {
        return HabitsDatabase.getInstance(provideAppContext())
    }

    @Provides
    @Singleton
    fun provideAppContext(): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideHabitsDataBaseDao(database: HabitsDatabase): HabitsDataBaseDao {
        return database.habitsDataBaseDao()
    }

    @Provides
    @Singleton
    fun provideHabitsApi(): HabitsApiService.HabitsApi {
        return HabitsApiService.HabitsApi()
    }
}