package ru.netology.recipes.adapter

import ru.netology.recipes.dto.Recipe

interface RecipeInteractionListener {
    fun onContentClicked(recipe: Recipe)
    fun onFavoriteClicked(recipe: Recipe)
    fun onShareClicked(recipe: Recipe)

    fun onDeleteClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
}