package ru.netology.recipes.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.utilsDO.Recipe

class RecipeRepositoryInMemory : RecipeRepository {

    private var nextId = GENERATED_RECIPES_AMOUNT.toLong()

    override val data = MutableLiveData(
        List(GENERATED_RECIPES_AMOUNT) { index ->
            Recipe(
                id = index + 1L,
                authorName = "Any author name $index",
                recipeName = "Any recipe name $index",
                content = "Any content $index",
            )
        }
    )

    private val recipes
        get() = checkNotNull(data.value)

    override fun favorite(recipeId: Long) {
        data.value = recipes.map {
            if (it.id != recipeId) it
            else it.copy(favorite = !it.favorite)
        }
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    private fun insert(recipe: Recipe) {
        data.value = listOf(
            recipe.copy(
                id = ++nextId
            )
        ) + recipes
    }

    private fun update(recipe: Recipe) {
        data.value = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
    }

    override fun delete(recipeId: Long) {
        data.value = recipes.filter { it.id != recipeId }
    }

    private companion object {
        const val GENERATED_RECIPES_AMOUNT = 1000
    }
}