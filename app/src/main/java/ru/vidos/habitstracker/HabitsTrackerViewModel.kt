package ru.vidos.habitstracker

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitTypes
import ru.vidos.habitstracker.models.SortTypes
import ru.vidos.habitstracker.utils.Resource

class HabitsTrackerViewModel(private val repository: HabitsTrackerRepository) : ViewModel() {

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
                repository.getHabits(habitType, searchInput, sortType.ordinal)
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
            withContext(Dispatchers.IO){
                repository.deleteHabit(habit)
            }
        }
    }

    private fun fetchHabits() {
        viewModelScope.launch {
            _status.value = Resource.HabitsApiStatus.LOADING
            try {
                repository.fetchHabits()
                _status.value = Resource.HabitsApiStatus.SUCCESS
            } catch (e: Exception) {
                _status.value = Resource.HabitsApiStatus.ERROR
            }
        }
    }
}

class HabitsTrackerViewModelFactory(
    private val repository: HabitsTrackerRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitsTrackerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}