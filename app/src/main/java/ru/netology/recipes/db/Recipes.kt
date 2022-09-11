package ru.netology.recipes.db

import ru.netology.recipes.dto.Recipe


internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    authorName = authorName,
    recipeName = recipeName,
    content = content,
    share = share,
    favorite = favorite,
    cuisine = cuisine
)

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    authorName = authorName,
    recipeName = recipeName,
    content = content,
    share = share,
    favorite = favorite,
    cuisine = cuisine
)