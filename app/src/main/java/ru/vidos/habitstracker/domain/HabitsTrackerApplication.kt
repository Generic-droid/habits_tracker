package ru.vidos.habitstracker.domain

import android.app.Application
import javax.inject.Inject

class HabitsTrackerApplication @Inject constructor() : Application() {

    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .habitsModule(HabitsModule(this))
            .build()
    }
}