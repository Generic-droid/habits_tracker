package ru.vidos.habitstracker.ui.home.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import ru.vidos.habitstracker.HabitsTrackerRepository
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.utils.HabitPriority
import ru.vidos.habitstracker.utils.HabitTypes

class AddEditHabitViewModel(
    private val repository: HabitsTrackerRepository, private val habit: Habit?
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

    private fun isFieldsNotEmpty(): Boolean {

        return when {
            currentHabit.value?.title.isNullOrBlank() -> {
                _titleError.value = "Empty title"
                false
            }
            currentHabit.value?.description.isNullOrBlank() -> {
                _descriptionError.value = "Empty description"
                false
            }
            currentHabit.value?.quantity.isNullOrBlank() -> {
                _quantityError.value = "Empty quantity"
                false
            }
            currentHabit.value?.periodicity.isNullOrBlank() -> {
                _periodicityError.value = "Empty periodicity"
                false
            }
            else -> {
                true
            }
        }
    }

    fun setHabitType(type: HabitTypes) {
        currentHabit.value?.type = type.name
    }

    fun saveHabit(): Boolean {
        return if (isFieldsNotEmpty()) {
            currentHabit.value?.let {
                repository.insertHabit(it)
            }
            true
        } else false
    }

    fun changeHabit(): Boolean {
        return if (isFieldsNotEmpty() && habit != null) {
            currentHabit.value?.let {
                repository.updateHabit(it)
            }
            true
        } else false
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