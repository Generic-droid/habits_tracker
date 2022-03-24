package ru.vidos.habitstracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vidos.habitstracker.data.Habit

class HabitsTrackerViewModel(private val repository: HabitsTrackerRepository) : ViewModel() {

    private val _habitsList = MutableLiveData<List<Habit>>()
    val habitsList: LiveData<List<Habit>> = _habitsList

    var currentHabit: Habit

    private val _titleError = MutableLiveData<String?>(null)
    val titleError: LiveData<String?> = _titleError

    private val _descriptionError = MutableLiveData<String?>(null)
    val descriptionError: LiveData<String?> = _descriptionError

    private val _quantityError = MutableLiveData<String?>(null)
    val quantityError: LiveData<String?> = _quantityError

    private val _periodicityError = MutableLiveData<String?>(null)
    val periodicityError: LiveData<String?> = _periodicityError

    init {
        _habitsList.value = repository.getHabits()

        currentHabit = Habit(
            id = 0,
            color = "#ADFF2F",
            title = "",
            description = "",
            priority = 0,
            type = true,
            quantity = "",
            periodicity = ""
        )
    }

    fun isFieldsNotEmpty(): Boolean {

        return when {
            currentHabit.title.isBlank() -> {
                _titleError.value = "Empty title"
                false
            }
            currentHabit.description.isBlank() -> {
                _descriptionError.value = "Empty description"
                false
            }
            currentHabit.quantity.isBlank() -> {
                _quantityError.value = "Empty quantity"
                false
            }
            currentHabit.periodicity.isBlank() -> {
                _periodicityError.value = "Empty periodicity"
                false
            }
            else -> { true }
        }
    }

    fun saveHabit () {
        currentHabit.id ++
        repository.addHabit(currentHabit)
    }
/*
    fun sortHabits(condition: Boolean) {
        val sortedList = mutableListOf<Habit>()
        habitsList.value?.forEach {
            Log.d("my", "Type: ${it.type}")
            if (it.type == condition) {
                sortedList.add(it)
            }
        }
        habitsList.value = sortedList

        Log.d("my", "List in model: $sortedList")
    }

 */
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