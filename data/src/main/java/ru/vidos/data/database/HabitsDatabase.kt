package ru.vidos.data.database

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import ru.vidos.data.models.HabitDto

@Database(entities = [HabitDto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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

class Converters {
    @TypeConverter
    fun listToJsonString(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

}