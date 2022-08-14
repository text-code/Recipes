package ru.netology.recipes.data

import androidx.lifecycle.LiveData
import ru.netology.recipes.utilsDO.Recipe

interface RecipeRepository {

    val data: LiveData<List<Recipe>>

    fun favorite(recipeId: Long)
    fun share(recipeId: Long)
    fun save(recipe: Recipe)
    fun delete(recipeId: Long)

    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}