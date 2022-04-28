package ru.vidos.habitstracker

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitTypes
import ru.vidos.habitstracker.models.SortTypes

class HabitsTrackerViewModel(private val repository: HabitsTrackerRepository) : ViewModel() {

    private val habitType = MutableLiveData<HabitTypes>()
    private val searchInput = MutableLiveData<String>()
    private val sortType = MutableLiveData(SortTypes.NONE)

    private val mediatorLiveData =
        MediatorLiveData<Triple<HabitTypes?, String?, SortTypes?>>().apply {
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

    init {
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
        habitType.value = type

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
        viewModelScope.launch { repository.deleteHabit(habit)}
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