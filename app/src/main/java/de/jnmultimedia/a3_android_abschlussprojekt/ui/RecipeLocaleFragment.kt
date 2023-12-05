package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.IngredientsUnit
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeLocaleBinding

class RecipeLocaleFragment : Fragment() {

    private lateinit var binding: FragmentRecipeLocaleBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeLocaleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.calls.observe(viewLifecycleOwner) { calls ->
//            adapter.submitList(calls)
//        }

        val testRecipes = mutableListOf<Recipe>()
        var nummer = 1
        repeat(20) {
            testRecipes.add(
                Recipe(
                    name = "Testname $nummer",
                    tags = listOf(),
                    categories = listOf(),
                    ingredients = listOf(
                        Ingredient(
                            name = "Reis",
                            count = 250,
                            unit = IngredientsUnit.GRAMM
                        )
                    )
                )
            )
            nummer++
        }

        viewModel.deleteAllRecipesInDatabase()
        testRecipes.forEach {
            viewModel.addRecipeToDatabase(it)
        }

        val adapter = RecipeAdapter(testRecipes)
        binding.rvRecipes.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}