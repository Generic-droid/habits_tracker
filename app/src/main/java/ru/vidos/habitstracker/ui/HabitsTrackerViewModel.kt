package ru.vidos.habitstracker.ui

import androidx.lifecycle.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vidos.data.models.HabitDto
import ru.vidos.data.models.HabitTypes
import ru.vidos.data.models.Resource
import ru.vidos.data.models.SortTypes
import ru.vidos.domain.usecases.DeleteHabitUseCase
import ru.vidos.domain.usecases.FetchHabitsUseCase
import ru.vidos.domain.usecases.GetHabitsUseCase
import ru.vidos.domain.usecases.UpdateHabitCountUseCase
import javax.inject.Inject

class HabitsTrackerViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val fetchHabitsUseCase: FetchHabitsUseCase,
    private val updateHabitCountUseCase: UpdateHabitCountUseCase,
) : ViewModel() {

    private val habitType = MutableLiveData<Int>()
    private val searchInput = MutableLiveData<String>()
    private val sortType = MutableLiveData(SortTypes.NONE)

    private val mediatorLiveData =
        MediatorLiveData<Triple<Int?, String?, SortTypes?>>().apply {
            addSource(habitType) {
                value = Triple(it, searchInput.value, sortType.value)
            }
            addSource(searchInput) {
                value = Triple(habitType.value, it, sortType.value)
            }
            addSource(sortType) {
                value = Triple(habitType.value, searchInput.value, it)
            }
        }

    private val _status = MutableLiveData<Resource.HabitsApiStatus>()
    val status: LiveData<Resource.HabitsApiStatus> = _status

    init {
        fetchHabits()
        searchInput.value = ""
    }

    // List of Habits to display to user
    val habitsList: LiveData<List<HabitDto>> =
        Transformations.switchMap(mediatorLiveData) { triple ->
            val habitType = triple.first
            val searchInput = triple.second
            val sortType = triple.third
            if (habitType != null && searchInput != null && sortType != null) {

                val habitEntitiesList = getHabitsUseCase(habitType, searchInput, sortType.ordinal)
                habitEntitiesList.map {
                    HabitDto.HabitDtoMapper.toDtoList(it)
                }.asLiveData()
            } else null
        }

    private fun fetchHabits() {
        viewModelScope.launch {
            _status.value = Resource.HabitsApiStatus.LOADING
            try {
                fetchHabitsUseCase.invoke()
                _status.value = Resource.HabitsApiStatus.SUCCESS
            } catch (e: Exception) {
                _status.value = Resource.HabitsApiStatus.ERROR
            }
        }
    }

    fun setHabitType(type: HabitTypes) {
        habitType.value = type.ordinal

    }

    fun setSearchInput(title: String) {
        searchInput.value = title

    }

    fun sortHabitsHighToLow() {
        sortType.value = SortTypes.ASC

    }

    fun sortHabitsLowToHigh() {
        sortType.value = SortTypes.DESC
    }

    fun deleteHabit(habitDto: HabitDto) {
        viewModelScope.launch {
            deleteHabitUseCase.invoke(
                HabitDto.HabitDtoMapper.mapFromDto(habitDto)
            )
        }
    }

    fun updateHabitCount(habitDto: HabitDto) {
        if (habitDto.count > 0) {
            viewModelScope.launch {
                habitDto.count = habitDto.count - 1
                updateHabitCountUseCase.invoke(
                    HabitDto.HabitDtoMapper.mapFromDto(habitDto)
                )
            }
        }
    }
}

class HabitsTrackerViewModelFactory(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val fetchHabitsUseCase: FetchHabitsUseCase,
    private val updateHabitCountUseCase: UpdateHabitCountUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitsTrackerViewModel(
                getHabitsUseCase,
                deleteHabitUseCase,
                fetchHabitsUseCase,
                updateHabitCountUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}