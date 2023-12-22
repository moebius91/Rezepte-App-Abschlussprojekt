package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeDetailTagAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditCategoryAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditIngredientAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditTagAdapter
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
        viewModel.inEditProcess.observe(viewLifecycleOwner) {
            if (!it) {
                println("Geht er rein, um alle zu wipen?")
                viewModel.wipeAllItems()
                viewModel.setInEditProcess()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*RV-Zutaten*/
        val adapterIngredients = RecipeEditIngredientAdapter(listOf(), viewModel, true, requireContext())
        binding.rvRecipeNewIngredients.adapter = adapterIngredients

        viewModel.selectedIngredients.observe(viewLifecycleOwner) {
            adapterIngredients.submitList(it)
        }

        /*RV-Schlagworte*/

        val adapterTags = RecipeDetailTagAdapter(listOf(), viewModel, true, requireContext())
        binding.rvRecipeNewTags.adapter = adapterTags

        viewModel.selectedTags.observe(viewLifecycleOwner) {
            adapterTags.submitList(it)
        }

        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        binding.rvRecipeNewTags.layoutManager = layoutManager

        /*RV-Kategorien*/

        val adapterCategories = RecipeEditCategoryAdapter(listOf(), viewModel, true, requireContext())
        binding.rvRecipeNewCategories.adapter = adapterCategories

        viewModel.selectedCategories.observe(viewLifecycleOwner) {
            adapterCategories.submitList(it)
        }

        binding.btnRecipeNewNewIngredient.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    name = binding.ettRecipeNewName.text.toString(),
                    description = binding.ettRecipeNewDescription.text.toString(),
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )
            )
            findNavController().navigate(R.id.ingredientsSelectionFragment)
        }

        binding.btnRecipeNewNewTag.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    name = binding.ettRecipeNewName.text.toString(),
                    description = binding.ettRecipeNewDescription.text.toString(),
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )
            )
            findNavController().navigate(R.id.tagsSelectionFragment)
        }

        binding.btnRecipeNewNewCategory.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    name = binding.ettRecipeNewName.text.toString(),
                    description = binding.ettRecipeNewDescription.text.toString(),
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )
            )
            findNavController().navigate(R.id.categoriesSelectionFragment)
        }

        binding.btnRecipeNewSave.setOnClickListener {
            val name = binding.ettRecipeNewName.text.toString()
            val description = binding.ettRecipeNewDescription.text.toString()
            val ingredients = viewModel.selectedIngredients.value
            val tags = viewModel.selectedTags.value
            val categories = viewModel.selectedCategories.value

            if (name != "" && description != "") {
                if (!ingredients.isNullOrEmpty()) {
                    val recipe = Recipe(
                        name = binding.ettRecipeNewName.text.toString(),
                        description = binding.ettRecipeNewDescription.text.toString(),
                        ingredients = ingredients,
                        tags = tags,
                        categories = categories
                    )

                    viewModel.addRecipeToDatabase(recipe)
                    viewModel.saveRecipeItem(recipe)
                    viewModel.outOfIngredientsSelection()
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