package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.IngredientAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.IngredientViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentIngredientsBinding

class IngredientsFragment: Fragment() {

    private lateinit var binding: FragmentIngredientsBinding
    private val viewModel: IngredientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngredientsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = IngredientAdapter(requireContext(), listOf(), viewModel)
        binding.rvIngredient.adapter = adapter

        viewModel.ingredients.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.ingredientNewFragment)
        }
    }
}