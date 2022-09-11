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

        //region Warning! Untested code Spinner
//        val arrayAdapter: ArrayAdapter<String>? = context?.let {
//            ArrayAdapter(it, R.array.list_of_cuisines, android.R.layout.simple_spinner_item )
//        }
//        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        with(binding.spinner) {
//            adapter = arrayAdapter
//            setSelection(0, false)
//            onItemSelectedListener = this@NewRecipeFragment
//            prompt = "Select cuisine"
//            gravity = Gravity.CENTER
//        }

//        val spinner = Spinner(context)
//        spinner.id = NEW_SPINNER_ID
//
//        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//
//        ll.setMargins(10, 40, 10, 10)
//        linearLayout.addView(spinner)

//        val spinner: Spinner = binding.spinner
//        spinner.adapter = arrayAdapter
        // endregion

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

    companion object {
        const val NEW_SPINNER_ID = 1
        var cuisine = 0
    }
}