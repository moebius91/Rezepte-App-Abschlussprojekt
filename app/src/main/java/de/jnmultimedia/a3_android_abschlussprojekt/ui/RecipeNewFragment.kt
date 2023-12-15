package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditIngredientAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeNewBinding

class RecipeNewFragment : Fragment() {

    private lateinit var binding: FragmentRecipeNewBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeNewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeEditIngredientAdapter(listOf(), viewModel, true)
        binding.rvRecipeNewIngredients.adapter = adapter


        binding.btnRecipeNewNewIngredient.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    name = binding.ettRecipeNewName.text.toString(),
                    description = binding.ettRecipeNewDescription.text.toString(),
                    ingredients = viewModel.ingredients.value ?: mutableListOf()
                )
            )
            findNavController().navigate(R.id.ingredientsSelectionFragment)
        }

        viewModel.selectedIngredients.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnRecipeNewSave.setOnClickListener {
            val name = binding.ettRecipeNewName.text.toString()
            val description = binding.ettRecipeNewDescription.text.toString()
            val ingredients = viewModel.selectedIngredients.value

            if (name != "" && description != "" && ingredients != null) {
                if (ingredients.isNotEmpty()) {
                    val recipe = Recipe(
                        name = name,
                        description = description,
                        ingredients = ingredients
                    )

                    viewModel.addRecipeToDatabase(recipe)
                    viewModel.saveRecipeItem(recipe)

                    findNavController().navigate(R.id.recipeDetailFragment)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Wir initialisieren eine Variable mit dem NavController
                findNavController().navigate(R.id.recipeLocaleFragment)
            }
        })
    }
}