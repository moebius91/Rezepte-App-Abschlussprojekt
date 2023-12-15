package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditIngredientAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment: Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeItem.observe(viewLifecycleOwner) {
            binding.tvRecipeDetailName.text = it.name
            binding.tvRecipeDetailDescription.text = it.description

//            val ingredientsCopy = it.ingredients.toList()
//            ingredientsCopy.forEach { ingredient ->
//                viewModel.addIngredientToRecipe(ingredient)
//            }

            binding.btnRecipeDetailEdit.setOnClickListener {
                viewModel.wipeIngredientsItem()
                findNavController().navigate(R.id.recipeEditFragment)
            }

            val adapter = RecipeEditIngredientAdapter(it.ingredients ?: listOf(), viewModel, false)
            binding.rvRecipeDetailIngredients.adapter = adapter

            binding.cvBtnRecipeDetailDelete.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Bestätigung")
                    .setMessage("Rezept wirklich löschen?")
                    .setPositiveButton("Löschen") { dialog, which ->
                        viewModel.deleteRecipeInDatabase(viewModel.recipeItem.value!!)
                        findNavController().navigate(R.id.recipeLocaleFragment)
                        Toast
                            .makeText(context, "Rezept gelöscht!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Abbrechen", null)
                    .show()
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