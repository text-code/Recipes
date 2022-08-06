package ru.netology.recipes.data

import androidx.lifecycle.LiveData
import ru.netology.recipes.utilsDO.Recipe

interface RecipeRepository {

    val data: LiveData<List<Recipe>>

    fun favorite(recipeId: Long)

}