package ru.vidos.domain.usecases

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.DataBaseRepository

internal class GetHabitsUseCaseTest {

    val dataBaseRepository = mock<DataBaseRepository>()

    val testHabit = Habit(
        1,
        1,
        5L,
        "",
        listOf(),
        1,
        1,
        "",
        1,
        ""
    )

    @Test
    fun `must return first element of Hot Flow collection from repository`() {

        Mockito.`when`(
            dataBaseRepository.getHabits(1, "", 0)).
        thenReturn(
            flowOf(
                listOf(testHabit, testHabit, testHabit)
            )
        )

        val useCase = GetHabitsUseCase(dataBaseRepository)

        runBlocking {

            val actual = useCase.invoke(1, "", 0).first()[0] // first() cancels the Flow
            val expected = testHabit

            assertEquals(expected, actual)
        }
    }
}