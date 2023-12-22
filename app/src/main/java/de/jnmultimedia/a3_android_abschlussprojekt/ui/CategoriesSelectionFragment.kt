package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.CategoriesSelectionAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentCategoriesSelectionBinding

class CategoriesSelectionFragment: Fragment() {

    private lateinit var binding: FragmentCategoriesSelectionBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoriesSelectionAdapter(listOf(), viewModel)
        binding.rvCategoriesSelection.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner) { categoriesMainList ->
            val newList = mutableListOf<Category>()

            categoriesMainList.forEach {
                newList.add(it)
            }

            viewModel.selectedCategories.observe(viewLifecycleOwner) {
                it.forEach { tag ->
                    newList.removeAll { it.name == tag.name }
                }
            }

            adapter.submitList(newList)
        }

        binding.btnCategorySelectionNewCategory.setOnClickListener {
            val name = binding.ettCategorySelectionNewCategoryName.text.toString()

            if (name != "") {
                viewModel.addCategoryToDatabase(Category(name = name))
                binding.ettCategorySelectionNewCategoryName.text.clear()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //viewModel.wipeAllItems()
                findNavController().navigateUp()
            }
        })
    }
}