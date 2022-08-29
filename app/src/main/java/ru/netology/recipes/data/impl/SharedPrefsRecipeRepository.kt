package ru.netology.recipes.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.dto.Recipe
import kotlin.properties.Delegates

class SharedPrefsRecipeRepository(
    application: Application
) : RecipeRepository {

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextId by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var recipes
        get() = checkNotNull(data.value)
        set(value) {
            prefs.edit {
                val serializedRecipe = Json.encodeToString(value)
                putString(RECIPE_PREFS_KEY, serializedRecipe)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Recipe>>

    init {
        val serializedRecipe = prefs.getString(RECIPE_PREFS_KEY, null)
        val recipes: List<Recipe> = if (serializedRecipe != null) {
            Json.decodeFromString(serializedRecipe)
        } else emptyList()
        data = MutableLiveData(recipes)
    }


    override fun favorite(recipeId: Long) {
        recipes = recipes.map {
            if (it.id != recipeId) it
            else it.copy(favorite = !it.favorite)
        }
    }

    override fun share(recipeId: Long) {
        recipes = recipes.map {
            if (it.id != recipeId) it
            // ??
            else it.copy(share = it.share + 1)
        }
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    private fun insert(recipe: Recipe) {
        recipes = listOf(
            recipe.copy(
                id = ++nextId
            )
        ) + recipes
    }

    private fun update(recipe: Recipe) {
        recipes = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
    }

    override fun delete(recipeId: Long) {
        recipes = recipes.filter { it.id != recipeId }
    }

    private companion object {
        const val RECIPE_PREFS_KEY = "recipes"
        const val NEXT_ID_PREFS_KEY = "nextId"
    }
}