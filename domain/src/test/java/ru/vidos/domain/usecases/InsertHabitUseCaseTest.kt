package ru.vidos.domain.usecases

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.vidos.domain.models.Habit
import ru.vidos.domain.repos.DataBaseRepository

internal class InsertHabitUseCaseTest {

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
    fun `must return Unit`() {

        val insertHabitUseCase = InsertHabitUseCase(dataBaseRepository)

        runBlocking {

            insertHabitUseCase.invoke(testHabit)

            val actual = insertHabitUseCase.invoke(testHabit)
            val expected = Unit

            assertEquals(expected, actual)
        }
    }
}