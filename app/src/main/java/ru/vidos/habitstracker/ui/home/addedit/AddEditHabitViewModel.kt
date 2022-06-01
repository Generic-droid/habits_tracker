package ru.vidos.habitstracker.ui.home.addedit

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vidos.data.models.HabitDto
import ru.vidos.data.models.HabitPriority
import ru.vidos.data.models.HabitTypes
import ru.vidos.domain.usecases.PutHabitUseCase
import ru.vidos.domain.usecases.UpdateHabitUseCase
import javax.inject.Inject

class AddEditHabitViewModel @Inject constructor(
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val putHabitUseCase: PutHabitUseCase,
    habitDto: HabitDto?
) : ViewModel() {

    private val _currentHabitDto = MutableLiveData<HabitDto>()
    val currentHabitDto: LiveData<HabitDto> get() = _currentHabitDto

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
        _currentHabitDto.value = habitDto ?: HabitDto(
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

        _color.value = currentHabitDto.value?.color
    }

    fun setHabitType(type: HabitTypes) {
        currentHabitDto.value?.type = type.ordinal
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
        _currentHabitDto.value?.color = color

        _color.value = color

    }

    fun saveHabit() {
        viewModelScope.launch {
            currentHabitDto.value?.let {
                putHabitUseCase.invoke(
                    HabitDto.HabitDtoMapper.mapFromDto(it)
                )
            }
        }
    }

    fun changeHabit() {
        viewModelScope.launch {
            currentHabitDto.value?.let {
                updateHabitUseCase.invoke(
                    HabitDto.HabitDtoMapper.mapFromDto(it)
                )
            }
        }
    }
}

class AddEditHabitViewModelFactory(
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val putHabitUseCase: PutHabitUseCase,
    private val habitDto: HabitDto?
    ) : Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditHabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddEditHabitViewModel(
                updateHabitUseCase,
                putHabitUseCase,
                habitDto) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}