package ru.netology.recipes.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.utilsDO.Recipe

class RecipeRepositoryInMemory : RecipeRepository {
    override val data = MutableLiveData(
        List(10) { index ->
            Recipe(
                id = index + 1L,
                authorName = "$index",
                recipeName = "afpwoerifh",
                recipeContent = "a;sdlkfh",
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
}