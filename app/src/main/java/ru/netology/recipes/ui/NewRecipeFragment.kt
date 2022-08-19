package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.ui.FeedFragment.Companion.textArg
import ru.netology.recipes.data.viewModel.RecipeViewModel
import ru.netology.recipes.databinding.FragmentNewRecipeBinding

class NewRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewRecipeBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArg?.let(binding.edit::setText)

        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()) {
                val content = binding.edit.text.toString()
                viewModel.onSaveButtonClicked(content)
            }
            findNavController().navigateUp()
        }

        return binding.root
    }
}