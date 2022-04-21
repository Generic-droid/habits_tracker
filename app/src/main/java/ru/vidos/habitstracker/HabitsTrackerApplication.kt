package ru.vidos.habitstracker

import android.app.Application
import ru.vidos.habitstracker.data.HabitsDatabase

class HabitsTrackerApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val habitsDatabase by lazy { HabitsDatabase.getInstance(this) }
    val habitsTrackerRepository by lazy { HabitsTrackerRepository(habitsDatabase.habitsDataBaseDao()) }
}