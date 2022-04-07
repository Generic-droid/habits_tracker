package ru.vidos.habitstracker.models

import java.io.Serializable

data class Habit(
    var id: Int,
    var color: String,
    var title: String,
    var description: String,
    var priority: Int,
    var type: String,
    var quantity: String,
    var periodicity: String
) : Serializable