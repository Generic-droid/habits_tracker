package ru.vidos.habitstracker.ui.home.addedit

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vidos.habitstracker.HabitsTrackerRepository
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitPriority
import ru.vidos.habitstracker.models.HabitTypes
import java.util.*

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

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int> get() = _color

    init {
        _currentHabit.value = habit ?: Habit(
            // id = 0,
            color = Color.BLACK,
            count = 0,
            date = 0,
            description = "",
            dates = listOf(),
            frequency = 0,
            priority = HabitPriority.HIGH.ordinal,
            title = "",
            type = HabitTypes.GOOD.ordinal,
            uid = ""
        )

        _color.value = currentHabit.value?.color
    }

    fun setHabitType(type: HabitTypes) {
        currentHabit.value?.type = type.ordinal
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

    fun setColor(color: Int) {
        _currentHabit.value?.color = color

        _color.value = color

    }

    fun saveHabit() {
        currentHabit.value?.let {
            MainScope().launch {
                it.date = Calendar.getInstance().time.time
                withContext(Dispatchers.IO) { repository.insertHabit(it) }
            }
        }
    }

    fun changeHabit() {
        currentHabit.value?.let {
            MainScope().launch {
                it.date = Calendar.getInstance().time.time
                withContext(Dispatchers.IO) { repository.updateHabit(it) }
            }
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