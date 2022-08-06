package ru.netology.recipes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.recipes.data.impl.RecipeAdapter
import ru.netology.recipes.data.viewModel.RecipeViewModel
import ru.netology.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecipeAdapter(viewModel::onFavoriteClicked)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(this) { recipes ->
            adapter.submitList(recipes)
        }
    }
}