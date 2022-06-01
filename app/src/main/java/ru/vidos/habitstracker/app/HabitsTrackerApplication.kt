package ru.vidos.habitstracker.app

import android.app.Application
import ru.vidos.habitstracker.di.AppModule
import ru.vidos.habitstracker.di.ApplicationComponent
import ru.vidos.habitstracker.di.DaggerApplicationComponent
import javax.inject.Inject

class HabitsTrackerApplication @Inject constructor() : Application() {

    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}