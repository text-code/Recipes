package ru.netology.recipes.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.recipes.adapter.RecipeInteractionListener
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.data.impl.FileRecipeRepository
import ru.netology.recipes.util.SingleLiveEvent
import ru.netology.recipes.utilsDO.Recipe

//class RecipeViewModel : ViewModel(), RecipeInteractionListener {
//    private val repository: RecipeRepository = RecipeRepositoryInMemory()

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {
    private val repository: RecipeRepository = FileRecipeRepository(application)

    val data by repository::data

    val shareEvent = SingleLiveEvent<String>()

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


    override fun onFavoriteClicked(recipe: Recipe) =
        repository.favorite(recipe.id)

    override fun onShareClicked(recipe: Recipe) {
        repository.share(recipe.id)
        shareEvent.value = recipe.content
    }

    override fun onDeleteClicked(recipe: Recipe) =
        repository.delete(recipe.id)

    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
    }
}