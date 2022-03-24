package ru.vidos.habitstracker

import android.app.Application

class HabitsTrackerApplication : Application() {

    val habitsTrackerRepository by lazy { HabitsTrackerRepository() }
}