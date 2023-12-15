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
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.IngredientsSelectionAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentIngredientsSelectionBinding

class IngredientsSelectionFragment: Fragment() {

    private lateinit var binding: FragmentIngredientsSelectionBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngredientsSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = IngredientsSelectionAdapter(listOf(), viewModel)
        binding.rvIngredientsSelection.adapter = adapter

        viewModel.ingredients.observe(viewLifecycleOwner) { ingredientsMainList ->
            val newList = mutableListOf<Ingredient>()

            ingredientsMainList.forEach {
                newList.add(it)
            }

            viewModel.selectedIngredients.observe(viewLifecycleOwner) {
                it.forEach {
                    if (newList.contains(it)) {
                        newList.remove(it)
                    }
                }
            }

            adapter.submitList(newList)
        }

        binding.btnIngrSelectionNewIngredient.setOnClickListener {
            val name = binding.ettIngrSelectionNewIngredientName.text.toString()

            if (name != "") {
                viewModel.addIngredientToDatabase(Ingredient(name = name))
                binding.ettIngrSelectionNewIngredientName.text.clear()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.wipeAllItems()
                findNavController().navigate(R.id.recipeEditFragment)
            }
        })
    }
}