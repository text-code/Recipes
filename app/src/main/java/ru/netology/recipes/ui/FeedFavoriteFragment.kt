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
import ru.netology.recipes.databinding.FragmentFeedBinding
import ru.netology.recipes.ui.FeedFragment.Companion.idArg
import ru.netology.recipes.ui.FeedFragment.Companion.shareEvent
import ru.netology.recipes.ui.FeedFragment.Companion.textArg

class FeedFavoriteFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

//    private val viewModel by viewModels<PostViewModel>()

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
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val favoriteRecipes = recipes.filter { it.favorite }
            adapter.submitList(favoriteRecipes)

            if (favoriteRecipes.isNotEmpty())
                binding.backgroundImage.visibility = View.INVISIBLE
        }

        binding.addRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_feedFavoriteFragment_to_newRecipeFragment)
        }

        viewModel.contentRecipe.observe(viewLifecycleOwner) { initialContent ->
            findNavController().navigate(
                R.id.action_feedFavoriteFragment_to_newRecipeFragment,
                Bundle().apply { textArg = initialContent }
            )
        }

        viewModel.selectedRecipe.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFavoriteFragment_to_selectedRecipeFragment,
                Bundle().apply { idArg = it.id }
            )
        }
        return binding.root
    }
}