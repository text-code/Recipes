package ru.netology.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.recipes.R
import ru.netology.recipes.databinding.RecipeBinding
import ru.netology.recipes.dto.Recipe
import kotlin.math.floor

internal class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecipeBinding,
        private val listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.favorite.setOnClickListener { listener.onFavoriteClicked(recipe) }
            binding.share.setOnClickListener { listener.onShareClicked(recipe) }
            binding.menu.setOnClickListener { popupMenu.show() }
            binding.recipe.setOnClickListener { listener.onRecipeClicked(recipe) }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            with(binding) {
                authorName.text = recipe.authorName
                recipeName.text = recipe.recipeName
                recipeContent.text = recipe.content
                favorite.isChecked = recipe.favorite
                share.text = counter(recipe.share)
                cuisine.text = cuisines[recipe.cuisine]
            }
        }

        private fun counter(value: Int) =
            when {
                (value in 1_000..1_099) -> "1K"
                (value in 1_100..9_999) -> (floor((value / 100).toDouble()) / 10).toString() + "K"
                (value in 10_000..999_999) -> (value / 1_000).toString() + "K"
                (value >= 1_000_000) -> (value / 1_000_000).toString() + "M"
                else -> value.toString()
            }

        private val cuisines =
            binding.root.context.resources.getStringArray(R.array.list_of_cuisines).toList()
    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }
}