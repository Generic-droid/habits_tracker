package ru.vidos.habitstracker.data

data class Habit (
    var id: Int,
    var color: String,
    var title: String,
    var description: String,
    var priority: Int,
    var type: Boolean,
    var quantity: String,
    var periodicity: String
)