package ru.netology.recipes.data.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.data.impl.RecipeRepositoryInMemory
import ru.netology.recipes.utilsDO.Recipe

class RecipeViewModel: ViewModel() {
    private val repository: RecipeRepository = RecipeRepositoryInMemory()

    val data by repository::data

    fun onFavoriteClicked(recipe: Recipe) = repository.favorite(recipe.id)
}