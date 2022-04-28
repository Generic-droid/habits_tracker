package ru.vidos.habitstracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.vidos.habitstracker.models.Habit

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitsDatabase : RoomDatabase() {

    abstract fun habitsDataBaseDao(): HabitsDataBaseDao

    companion object {

        /**
         * The value of a volatile variable will never be cached,
         * and all writes and reads will be done to and from the main memory.
         * This helps make sure the value of INSTANCE is always up-to-date
         * and the same for all execution threads.
         * It means that changes made by one thread to INSTANCE are visible
         * to all other threads immediately.
         */
        @Volatile
        private var INSTANCE: HabitsDatabase? = null

        /**
         * Singleton prevents multiple instances of database opening at the
         * same time.
         */
        fun getInstance(context: Context): HabitsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                // If the INSTANCE is null - create the database, otherwise return it.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HabitsDatabase::class.java,
                        "habits_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}