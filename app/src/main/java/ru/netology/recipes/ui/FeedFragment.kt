package ru.netology.recipes.ui

import android.content.Context
import android.content.Intent
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
import ru.netology.recipes.util.StringArg

class FeedFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.shareEvent.observe(this) { postContent ->
            context?.let { shareEvent(it, postContent) }
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
            adapter.submitList(recipes)
        }

        binding.addRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newRecipeFragment)
        }

        viewModel.contentRecipe.observe(viewLifecycleOwner) { initialContent ->
            findNavController().navigate(
                R.id.action_feedFragment_to_newRecipeFragment,
                Bundle().apply { textArg = initialContent }
            )
        }

        viewModel.selectedRecipe.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_selectedRecipeFragment,
                Bundle().apply { idArg = it.id }
            )
        }
        return binding.root
    }


    companion object {
        private const val ID_POST_KEY = "ID_POST"

        var Bundle.textArg: String? by StringArg

        var Bundle.idArg: Long
            set(value) = putLong(ID_POST_KEY, value)
            get() = getLong(ID_POST_KEY)

        fun shareEvent(context: Context, postContent: String?) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"

                putExtra(Intent.EXTRA_TEXT, postContent)
            }

            val shareIntent = Intent.createChooser(intent, "Share")
            context.startActivity(shareIntent)
        }
    }
}