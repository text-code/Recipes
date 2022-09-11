package ru.netology.recipes.dto

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Long,
    val recipeName: String,
    val authorName: String,
    val content: String,
    val share: Int = 0,
    val favorite: Boolean = false,
    val cuisine: Int
)
