package ru.netology.recipes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.data.viewModel.RecipeViewModel
import ru.netology.recipes.databinding.FragmentNewRecipeBinding
import ru.netology.recipes.ui.FeedFragment.Companion.textArg

class NewRecipeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var cuisine = 0

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewRecipeBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArg?.let(binding.edit::setText)


        binding.edit.requestFocus()

        //region Spinner from documentation
        val spinner: Spinner = binding.spinner
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.list_of_cuisines,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        //endregion

        //region Add image
//        val image = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
//        }
//
//        binding.addImageRecipe.setOnClickListener {
//            image.launch(arrayOf("image/*"))
//        }
        //endregion

        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()) {
                val recipeName = binding.recipeName.text.toString()
                val authorName = binding.authorName.text.toString()
                val content = binding.edit.text.toString()


                viewModel.onSaveButtonClicked(recipeName, authorName, content, cuisine)
            }
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        cuisine = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}