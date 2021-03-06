package ru.vidos.domain.models

data class Habit(

    val color: Int,

    val count: Int,

    val date: Long,

    val description: String,

    val dates: List<Int>,

    val frequency: Int,

    val priority: Int,

    val title: String,

    val type: Int,

    val uid: String
)