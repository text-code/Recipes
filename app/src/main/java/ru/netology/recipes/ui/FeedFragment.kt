package ru.netology.recipes.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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

        viewModel.filterData.observe(viewLifecycleOwner) { filterRecipe ->
            adapter.submitList(filterRecipe)
        }

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)

            if (recipes.isNotEmpty())
                binding.backgroundImage.visibility = View.INVISIBLE
        }

        binding.addRecipe.setOnClickListener {
//            image.launch(arrayOf("image/*"))
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.options_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        val searchView = menuItem.actionView as SearchView
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return false
                            }


                            override fun onQueryTextChange(newText: String?): Boolean {

                                if (!newText.isNullOrBlank())
                                    viewModel.filter(newText)

                                return false
                            }
                        })
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


//    private fun filter(newText: String?) {
//        val binding = FragmentFeedBinding.inflate(layoutInflater)
//        val adapter = RecipeAdapter(viewModel)
//        binding.recipeRecyclerView.adapter = adapter
//
//        val recipes = viewModel.data.value
//        val searchRecipes = recipes?.filter { it.recipeName == newText }
//
//        adapter.submitList(searchRecipes)
//
//
////        viewModel.data.observe(viewLifecycleOwner) { recipes ->
////            val searchRecipe = recipes.filter { it.recipeName == query }
////
////                                    if (searchRecipe.isNotEmpty())
////            adapter.submitList(searchRecipe)
////        }
//    }


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