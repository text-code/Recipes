package ru.netology.recipes.dto

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Long,
    val authorName: String,
    val recipeName: String,
    val content: String,
    val favorite: Boolean = false,
    val share: Boolean = false
)
