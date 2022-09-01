package ru.netology.recipes.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.recipes.data.viewModel.RecipeViewModel
import ru.netology.recipes.databinding.SearchBinding

class SearchableActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.search)

        val binding = SearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val adapter = RecipeAdapter(viewModel)
//        binding.searchFragment.recipeRecyclerView.adapter = adapter


        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
        }
    }

    private fun doMySearch(query: String) {
        TODO("Not yet implemented")
    }
}