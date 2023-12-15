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
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeEditBinding

class RecipeEditFragment : Fragment() {

    private lateinit var binding: FragmentRecipeEditBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var selectedIngredients: List<Ingredient> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeEditIngredientAdapter(listOf(), viewModel, true)
        binding.rvRecipeEditIngredients.adapter = adapter

        viewModel.recipeItem.observe(viewLifecycleOwner) { recipe ->
            binding.tvRecipeEditTitle.text = "Rezept bearbeiten:"

            binding.ettRecipeEditName.setText(recipe.name)
            binding.ettRecipeEditDescription.setText(recipe.description)

            recipe.ingredients?.forEach { viewModel.addIngredientToRecipe(it) }

            adapter.submitList(recipe.ingredients!!)
        }

        viewModel.selectedIngredients.observe(viewLifecycleOwner) { ingredients ->
            selectedIngredients = ingredients
        }

        binding.btnRecipeEditNewIngredient.setOnClickListener {
            viewModel.inIngredientsSelection()
            findNavController().navigate(R.id.ingredientsSelectionFragment)
        }

        binding.btnRecipeEditSave.setOnClickListener {
            val name = binding.ettRecipeEditName.text.toString()
            val description = binding.ettRecipeEditDescription.text.toString()

            if (name != "" && description != "" && selectedIngredients.isNotEmpty()) {
                val recipe = Recipe(name = name, description = description, ingredients = selectedIngredients)

                viewModel.updateRecipeInDatabase(recipe)
                viewModel.saveRecipeItem(recipe)

                findNavController().navigate(R.id.recipeDetailFragment)
            } else {
                println("Fehler: Eingaben oder Zutatenliste unvollständig")
                println("Name: $name")
                println("Beschreibung: $description")
                println("Zutaten: $selectedIngredients")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.recipeDetailFragment)
            }
        })
    }
}