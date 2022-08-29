package ru.netology.recipes.data.impl

import androidx.lifecycle.map
import ru.netology.recipes.db.RecipeDao
import ru.netology.recipes.db.toEntity
import ru.netology.recipes.db.toModel
import ru.netology.recipes.data.RecipeRepository
import ru.netology.recipes.dto.Recipe

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == 0L) dao.insert(recipe.toEntity())
        else dao.updateContentById(recipe.id, recipe.content)
    }

    override fun favorite(recipeId: Long) {
        dao.likeByMe(recipeId)
    }

    override fun share(recipeId: Long) {
        dao.share(recipeId)
    }

    override fun delete(recipeId: Long) {
        dao.removeById(recipeId)
    }
}
