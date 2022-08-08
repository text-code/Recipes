package ru.netology.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.recipes.R
import ru.netology.recipes.databinding.RecipeBinding
import ru.netology.recipes.utilsDO.Recipe

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
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            with(binding) {
                authorName.text = recipe.authorName
                recipeName.text = recipe.recipeName
                recipeContent.text = recipe.content
                favorite.setImageResource(getFavoriteIconResId(recipe.favorite))
                menu.setOnClickListener { popupMenu.show() }
            }
        }

        private fun getFavoriteIconResId(favorite: Boolean) =
            if (favorite) R.drawable.ic_star_gold_24dp else R.drawable.ic_star_border_24dp
    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem

    }
}