package ru.netology.recipes.adapter

import ru.netology.recipes.utilsDO.Recipe

interface RecipeInteractionListener {
    fun onFavoriteClicked(recipe: Recipe)
    fun onDeleteClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
}