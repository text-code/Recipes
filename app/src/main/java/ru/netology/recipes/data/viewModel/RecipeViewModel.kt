package ru.netology.recipes.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.recipes.adapter.RecipeInteractionListener
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.data.impl.RecipeRepositoryImpl
import ru.netology.recipes.db.AppDb
import ru.netology.recipes.dto.Recipe
import ru.netology.recipes.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

//    private val repository: RecipeRepository = SharedPrefsRecipeRepository(application)


    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).recipeDao
    )

    val data by repository::data

    val shareEvent = SingleLiveEvent<String>()

    val contentRecipe = SingleLiveEvent<String>()

    val selectedRecipe = SingleLiveEvent<Recipe>()

    val filterData = MutableLiveData<List<Recipe>>()

    private val currentRecipe = MutableLiveData<Recipe?>()

    fun onSaveButtonClicked(recipeName: String, authorName: String, content: String, cuisine: Int) {
        if (content.isBlank()) return
        val recipe = currentRecipe.value?.copy(
            content = content
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            recipeName = recipeName,
            authorName = authorName,
            content = content,
            cuisine = cuisine
        )
        repository.save(recipe)
        currentRecipe.value = null
    }

    fun filter(text: String) {
        filterData.value = data.value?.filter { it.recipeName.startsWith(text) }
    }

    fun filterCuisines(position: Int) {
        if (position != 0)
            filterData.value = data.value?.filter { it.cuisine == position }
        else
            filterData.value = data.value
    }

    override fun onRecipeClicked(recipe: Recipe) {
        selectedRecipe.value = recipe
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
        contentRecipe.value = recipe.content
    }
}