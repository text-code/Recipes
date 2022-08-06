package ru.netology.recipes.utilsDO

data class Recipe(
    val id: Long,
    val authorName: String,
    val recipeName: String,
    val recipeContent: String,
    val favorite: Boolean = false
)
