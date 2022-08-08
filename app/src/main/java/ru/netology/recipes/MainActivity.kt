package ru.netology.recipes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.data.viewModel.RecipeViewModel
import ru.netology.recipes.databinding.ActivityMainBinding
import ru.netology.recipes.util.hideKeyboard

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(this) { recipes ->
            adapter.submitList(recipes)
        }

        binding.saveButton.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)

                hideKeyboard()
                clearFocus()
            }
        }

        viewModel.currentRecipe.observe(this) { currentRecipe ->
            binding.content.setText(currentRecipe?.content)
        }
    }
}