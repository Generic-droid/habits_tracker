package ru.vidos.domain.usecases

import ru.vidos.domain.repos.NetworkRepository


class FetchHabitsUseCase ( private val networkRepository: NetworkRepository ) {
    suspend operator fun invoke() {
        networkRepository.fetchHabits()
    }
}