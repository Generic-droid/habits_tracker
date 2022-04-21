package ru.vidos.habitstracker

import androidx.lifecycle.*
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.utils.HabitTypes

class HabitsTrackerViewModel(private val repository: HabitsTrackerRepository) : ViewModel() {

    private val _habitsList = MutableLiveData<List<Habit>>()

    // List of Habits to display to user
    val habitsList: LiveData<List<Habit>> = repository.getHabits()

    fun filterHabitsByType(type: HabitTypes) {

        _habitsList.value =
            repository.getHabits().value?.filter { it.type == type.name }

    }

    fun filterHabitsByTitle(title: String) {

        _habitsList.value =
            repository.getHabits().value?.filter { it.title.startsWith(title) }

    }

    fun sortHabitsHighToLow() {

        _habitsList.value =
            repository.getHabits().value?.sortedBy { it.priority }

    }

    fun sortHabitsLowToHigh() {

        _habitsList.value =
            repository.getHabits().value?.sortedByDescending { it.priority }
    }

    fun deleteHabit(habit: Habit) {
        repository.deleteHabit(habit)
    }
}

class HabitsTrackerViewModelFactory(private val repository: HabitsTrackerRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitsTrackerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}