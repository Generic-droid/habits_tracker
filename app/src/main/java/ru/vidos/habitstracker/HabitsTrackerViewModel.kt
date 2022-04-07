package ru.vidos.habitstracker

import androidx.lifecycle.*
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.utils.HabitTypes

class HabitsTrackerViewModel(private val repository: HabitsTrackerRepository) : ViewModel() {

    // List of Habits to display to user
    private val _habitsList = MutableLiveData<List<Habit>>()
    val habitsList: LiveData<List<Habit>> get() = _habitsList

    init {
        _habitsList.value = repository.getHabits()
    }

    fun sortHabitsByType(type: HabitTypes) {

        _habitsList.value =
            repository.getHabits().filter { it.type == type.name }

    }

    fun sortHabitsByTitle(title: String) {

        _habitsList.value =
            repository.getHabits().filter { it.title.startsWith(title) }
    }

    fun sortHabitsHighToLow() {

        _habitsList.value =
            repository.getHabits().sortedBy { it.priority }

    }

    fun sortHabitsLowToHigh() {

        _habitsList.value =
            repository.getHabits().sortedByDescending { it.priority }
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