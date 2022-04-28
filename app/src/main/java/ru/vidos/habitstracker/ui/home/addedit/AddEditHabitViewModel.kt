package ru.vidos.habitstracker.ui.home.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vidos.habitstracker.HabitsTrackerRepository
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitPriority
import ru.vidos.habitstracker.models.HabitTypes

class AddEditHabitViewModel(
    private val repository: HabitsTrackerRepository, habit: Habit?
) : ViewModel() {

    private val _currentHabit = MutableLiveData<Habit>()
    val currentHabit: LiveData<Habit> get() = _currentHabit

    private val _titleError = MutableLiveData<String?>(null)
    val titleError: LiveData<String?> = _titleError

    private val _descriptionError = MutableLiveData<String?>(null)
    val descriptionError: LiveData<String?> = _descriptionError

    private val _quantityError = MutableLiveData<String?>(null)
    val quantityError: LiveData<String?> = _quantityError

    private val _periodicityError = MutableLiveData<String?>(null)
    val periodicityError: LiveData<String?> = _periodicityError

    init {
        _currentHabit.value =
            if (habit != null) {
                Habit(
                    id = habit.id,
                    color = habit.color,
                    title = habit.title,
                    description = habit.description,
                    priority = habit.priority,
                    type = habit.type,
                    quantity = habit.quantity,
                    periodicity = habit.periodicity
                )
            } else {
                Habit(
                    id = 0,
                    color = "#ADFF2F",
                    title = "",
                    description = "",
                    priority = HabitPriority.HIGH.ordinal,
                    type = HabitTypes.GOOD.name,
                    quantity = "",
                    periodicity = ""
                )
            }
    }

    fun setHabitType(type: HabitTypes) {
        currentHabit.value?.type = type.name
    }

    fun setTitleError(titleError: String) {
        _titleError.value = titleError
    }

    fun setDescriptionError(descriptionError: String) {
        _descriptionError.value = descriptionError
    }

    fun setQuantityError(quantityError: String) {
        _quantityError.value = quantityError
    }

    fun setPeriodicityError(periodicityError: String) {
        _periodicityError.value = periodicityError
    }

    fun saveHabit() {
        currentHabit.value?.let {
            viewModelScope.launch { repository.insertHabit(it) }
        }
    }

    fun changeHabit() {
        currentHabit.value?.let {
            viewModelScope.launch { repository.updateHabit(it) }
        }
    }
}

class AddEditHabitViewModelFactory(
    private val repository: HabitsTrackerRepository, private val habit: Habit?
) : Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditHabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddEditHabitViewModel(repository, habit) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}