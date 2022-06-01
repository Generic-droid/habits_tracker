package ru.vidos.habitstracker.di

import dagger.Module
import dagger.Provides
import ru.vidos.data.database.DataBaseRepositoryImpl
import ru.vidos.data.network.NetworkRepositoryImpl
import ru.vidos.domain.usecases.*
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideGetHabitsUseCase(dataBaseRepositoryImpl: DataBaseRepositoryImpl): GetHabitsUseCase {
        return GetHabitsUseCase(dataBaseRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideInsertHabitUseCase(
        dataBaseRepositoryImpl: DataBaseRepositoryImpl
    ): InsertHabitUseCase {
        return InsertHabitUseCase(dataBaseRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideUpdateHabitUseCase(
        networkRepositoryImpl: NetworkRepositoryImpl,
        dataBaseRepositoryImpl: DataBaseRepositoryImpl
    ): UpdateHabitUseCase {
        return UpdateHabitUseCase(networkRepositoryImpl, dataBaseRepositoryImpl)
    }

    @Provides
    @Singleton
    fun providePutHabitUseCase(
        networkRepositoryImpl: NetworkRepositoryImpl
    ): PutHabitUseCase {
        return PutHabitUseCase(networkRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideDeleteHabitUseCase(
        networkRepositoryImpl: NetworkRepositoryImpl,
        dataBaseRepositoryImpl: DataBaseRepositoryImpl
    ): DeleteHabitUseCase {
        return DeleteHabitUseCase(networkRepositoryImpl,dataBaseRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideFetchHabitsUseCase(
        networkRepositoryImpl: NetworkRepositoryImpl
    ): FetchHabitsUseCase {
        return FetchHabitsUseCase(networkRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideInsertHabitsUseCase(
        dataBaseRepositoryImpl: DataBaseRepositoryImpl
    ): InsertHabitsUseCase {
        return InsertHabitsUseCase(dataBaseRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideUpdateHabitCountUseCase(
        networkRepositoryImpl: NetworkRepositoryImpl,
        dataBaseRepositoryImpl: DataBaseRepositoryImpl
    ): UpdateHabitCountUseCase {
        return UpdateHabitCountUseCase(
            networkRepositoryImpl,
            dataBaseRepositoryImpl
        )
    }
}