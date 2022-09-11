package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.data.viewModel.RecipeViewModel
import ru.netology.recipes.databinding.FragmentSelectedRecipeBinding
import ru.netology.recipes.ui.FeedFragment.Companion.idArg
import ru.netology.recipes.ui.FeedFragment.Companion.shareEvent
import ru.netology.recipes.ui.FeedFragment.Companion.textArg

class SelectedRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.shareEvent.observe(this) { recipeContent ->
            context?.let { shareEvent(it, recipeContent) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSelectedRecipeBinding.inflate(inflater, container, false)

        val viewHolder = RecipeAdapter.ViewHolder(binding.recipeLayout, viewModel)

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.firstOrNull { it.id == arguments?.idArg }
            if (recipe != null) {
                viewHolder.bind(recipe)
            } else {
                findNavController().navigateUp()
            }
        }

        viewModel.contentRecipe.observe(viewLifecycleOwner) { text ->
            findNavController().navigate(
                R.id.action_selectedRecipeFragment_to_newRecipeFragment,
                Bundle().apply { textArg = text }
            )
        }

        viewModel.selectedRecipe.observe(viewLifecycleOwner) {
        }

        return binding.root
    }
}