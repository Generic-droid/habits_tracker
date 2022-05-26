package ru.vidos.habitstracker.ui

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vidos.habitstracker.domain.models.Habit
import ru.vidos.habitstracker.domain.models.HabitTypes
import ru.vidos.habitstracker.domain.models.SortTypes
import ru.vidos.habitstracker.domain.usecases.DeleteHabitUseCase
import ru.vidos.habitstracker.domain.usecases.FetchHabitsUseCase
import ru.vidos.habitstracker.domain.usecases.GetHabitsUseCase
import ru.vidos.habitstracker.utils.Resource
import javax.inject.Inject

class HabitsTrackerViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val fetchHabitsUseCase: FetchHabitsUseCase
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
    val habitsList: LiveData<List<Habit>> =
        Transformations.switchMap(mediatorLiveData) { triple ->
            val habitType = triple.first
            val searchInput = triple.second
            val sortType = triple.third
            if (habitType != null && searchInput != null && sortType != null) {
                getHabitsUseCase(habitType, searchInput, sortType.ordinal).asLiveData()
            } else null

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

    fun deleteHabit(habit: Habit) {
        MainScope().launch {
            _status.value = Resource.HabitsApiStatus.LOADING
            try {
            withContext(Dispatchers.IO){
                deleteHabitUseCase.invoke(habit)
            }
                _status.value = Resource.HabitsApiStatus.SUCCESS
            } catch (e: Exception) {
                _status.value = Resource.HabitsApiStatus.ERROR
            }
        }
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
}

class HabitsTrackerViewModelFactory(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val fetchHabitsUseCase: FetchHabitsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitsTrackerViewModel(
                getHabitsUseCase,
                deleteHabitUseCase,
                fetchHabitsUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}