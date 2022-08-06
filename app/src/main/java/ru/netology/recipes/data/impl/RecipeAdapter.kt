package ru.netology.recipes.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.recipes.R
import ru.netology.recipes.databinding.RecipeBinding
import ru.netology.recipes.utilsDO.Recipe

internal class RecipeAdapter(
    private val onFavoriteClicked: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) = with(binding) {
            authorName.text = recipe.authorName
            recipeName.text = recipe.recipeName
            recipeContent.text = recipe.recipeContent
            favorite.setImageResource(getFavoriteIconResId(recipe.favorite))
            favorite.setOnClickListener { onFavoriteClicked(recipe) }
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