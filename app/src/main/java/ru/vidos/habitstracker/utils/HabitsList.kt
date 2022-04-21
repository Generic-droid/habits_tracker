package ru.vidos.habitstracker.utils

import ru.vidos.habitstracker.models.Habit

class HabitsList {

    fun getHabits(): MutableList<Habit>{
        return mutableListOf(
            Habit(0, "#ADFF2F", "Swimming", "Swim ten kilometers per weekly.", HabitPriority.HIGH.ordinal, HabitTypes.GOOD.name, "5", "Every week" ),
            Habit(1, "#C41E3A", "Smoking", "Smoke ten cigarettes daily", HabitPriority.LOW.ordinal, HabitTypes.BAD.name, "8", "Every day" ),
            Habit(2, "#ADFF2F", "Coding", "Code one hour daily.", HabitPriority.HIGH.ordinal, HabitTypes.GOOD.name, "1", "Every day" ),
            Habit(3, "#ADFF2F", "Cooking", "Prepare dinner daily.", HabitPriority.MEDIUM.ordinal, HabitTypes.GOOD.name, "1", "Every day" ),
            Habit(4, "#ADFF2F", "Teeth cleaning", "Brush teeth 2 times daily.", HabitPriority.HIGH.ordinal, HabitTypes.GOOD.name, "2", "Every day" )
        )
    }
}