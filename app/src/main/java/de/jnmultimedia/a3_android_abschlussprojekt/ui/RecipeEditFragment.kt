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
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeEditBinding

class RecipeEditFragment : Fragment() {

    private lateinit var binding: FragmentRecipeEditBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var selectedIngredients: List<Ingredient> = listOf()
    private var selectedTags: List<Tag> = listOf()
    private var selectedCategories: List<Category> = listOf()

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
        var recipeId = 0

        val adapterIngredients = RecipeEditIngredientAdapter( listOf(), viewModel, true, requireContext())
        binding.rvRecipeEditIngredients.adapter = adapterIngredients

        val adapterTags = RecipeDetailTagAdapter( listOf(), viewModel, true, requireContext())
        binding.rvRecipeEditTags.adapter = adapterTags

        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        binding.rvRecipeEditTags.layoutManager = layoutManager

        val adapterCategories = RecipeEditCategoryAdapter( listOf(), viewModel, true, requireContext())
        binding.rvRecipeEditCategories.adapter = adapterCategories

        viewModel.recipeItem.observe(viewLifecycleOwner) { recipe ->
            recipeId = recipe.id ?: 0

            println("Rezept ID:")
            println(recipe.id)

            binding.tvRecipeEditTitle.text = "Rezept bearbeiten:"

            binding.ettRecipeEditName.setText(recipe.name)
            binding.ettRecipeEditDescription.setText(recipe.description)

            viewModel.inEditProcess.observe(viewLifecycleOwner) {
                if (!it) {
                    viewModel.wipeAllIngredientItems()
                    viewModel.setInEditProcess()
                }
            }

            if(recipe.ingredients != null) {
                adapterIngredients.submitList(recipe.ingredients!!)
                if (viewModel.inEditProcess.value != true) {
                    selectedIngredients = recipe.ingredients!!
                    viewModel.saveSelectedIngredients(recipe.ingredients!!.toMutableList())
                    viewModel.selectedIngredients.observe(viewLifecycleOwner) {
                        adapterIngredients.submitList(it)
                    }
                } else {
                    viewModel.selectedIngredients.observe(viewLifecycleOwner) {
                        selectedIngredients = it
                        adapterIngredients.submitList(it)
                    }
                }
            }

            if(recipe.tags != null) {
                adapterTags.submitList(recipe.tags)
                if (viewModel.inEditProcess.value != true) {
                    selectedTags = recipe.tags
                    viewModel.saveSelectedTags(recipe.tags.toMutableList())
                    viewModel.selectedTags.observe(viewLifecycleOwner) {
                        adapterTags.submitList(it)
                    }
                } else {
                    viewModel.selectedTags.observe(viewLifecycleOwner) {
                        selectedTags = it
                        adapterTags.submitList(it)
                    }
                }
            }

            if(recipe.categories != null) {
                adapterCategories.submitList(recipe.categories)
                if (viewModel.inEditProcess.value != true) {
                    selectedCategories = recipe.categories
                    viewModel.saveSelectedCategories(recipe.categories.toMutableList())
                    viewModel.selectedCategories.observe(viewLifecycleOwner) {
                        adapterCategories.submitList(it)
                    }
                } else {
                    viewModel.selectedCategories.observe(viewLifecycleOwner) {
                        selectedCategories = it
                        adapterCategories.submitList(it)
                    }
                }
            }
        }

        binding.btnRecipeEditNewIngredient.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    id = recipeId,
                    name = binding.ettRecipeEditName.text.toString(),
                    description = binding.ettRecipeEditDescription.text.toString(),
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )
            )
            findNavController().navigate(R.id.ingredientsSelectionFragment)
        }

        binding.btnRecipeEditNewTag.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    id = recipeId,
                    name = binding.ettRecipeEditName.text.toString(),
                    description = binding.ettRecipeEditDescription.text.toString(),
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )
            )
            findNavController().navigate(R.id.tagsSelectionFragment)
        }

        binding.btnRecipeEditNewCategory.setOnClickListener {
            viewModel.inIngredientsSelection()
            viewModel.saveRecipeItem(
                Recipe(
                    id = recipeId,
                    name = binding.ettRecipeEditName.text.toString(),
                    description = binding.ettRecipeEditDescription.text.toString(),
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )
            )
            findNavController().navigate(R.id.categoriesSelectionFragment)
        }

        binding.btnRecipeEditSave.setOnClickListener {
            val name = binding.ettRecipeEditName.text.toString()
            val description = binding.ettRecipeEditDescription.text.toString()

            if (name != "" && description != "" && selectedIngredients.isNotEmpty()) {
                val updatedRecipe = Recipe(
                    id = recipeId,
                    name = name,
                    description = description,
                    ingredients = viewModel.selectedIngredients.value,
                    tags = viewModel.selectedTags.value,
                    categories = viewModel.selectedCategories.value
                )

                viewModel.updateRecipeByIdInDatabase(updatedRecipe)
                viewModel.outOfIngredientsSelection()
                viewModel.saveRecipeItem(updatedRecipe)
                findNavController().navigateUp()
            } else {
                /*Debuggingblock*/
                println("Fehler:")
                println(name)
                println(description)
                println(selectedIngredients)
                println(selectedTags)
                println(selectedCategories)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }
}