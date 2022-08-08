package ru.netology.recipes.utilsDO

data class Recipe(
    val id: Long,
    val authorName: String,
    val recipeName: String,
    val content: String,
    val favorite: Boolean = false
)
