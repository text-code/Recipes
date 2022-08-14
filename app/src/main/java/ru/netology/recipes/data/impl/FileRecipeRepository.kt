package ru.netology.recipes.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.utilsDO.Recipe
import kotlin.properties.Delegates

class FileRecipeRepository(
    private val application: Application
) : RecipeRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Recipe::class.java).type

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
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Recipe>>

    init {
        val recipeFile = application.filesDir.resolve(FILE_NAME)
        val recipes: List<Recipe> = if (recipeFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
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
            else it.copy(share = true)
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
        const val NEXT_ID_PREFS_KEY = "nextId"
        const val FILE_NAME = "recipes.json"
    }
}