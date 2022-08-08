package ru.netology.recipes.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.recipes.adapter.RecipeInteractionListener
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.data.impl.RecipeRepositoryInMemory
import ru.netology.recipes.utilsDO.Recipe

class RecipeViewModel : ViewModel(), RecipeInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryInMemory()

    val data by repository::data

    val currentRecipe = MutableLiveData<Recipe?>()

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val recipe = currentRecipe.value?.copy(
            content = content
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            authorName = "Author",
            recipeName = "Recipe Name",
            content = content
        )
        repository.save(recipe)
        currentRecipe.value = null
    }


    override fun onFavoriteClicked(recipe: Recipe) = repository.favorite(recipe.id)
    override fun onDeleteClicked(recipe: Recipe) = repository.delete(recipe.id)
    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
    }
}