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
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeDetailTagAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditCategoryAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditIngredientAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeEditTagAdapter
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
        viewModel.disableInEditProcess()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeItem.observe(viewLifecycleOwner) {
            binding.tvRecipeDetailName.text = it.name
            binding.tvRecipeDetailDescription.text = it.description

            val adapterIngredients = RecipeEditIngredientAdapter(it.ingredients ?: listOf(), viewModel, false, requireContext())
            binding.rvRecipeDetailIngredients.adapter = adapterIngredients

            val adapterTags = RecipeDetailTagAdapter(it.tags ?: listOf(), viewModel, false, requireContext())
            binding.rvRecipeDetailTags.adapter = adapterTags

            val layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
            binding.rvRecipeDetailTags.layoutManager = layoutManager

            val adapterCategories = RecipeEditCategoryAdapter(it.categories ?: listOf(), viewModel, false, requireContext())
            binding.rvRecipeDetailCategories.adapter = adapterCategories

            binding.cvBtnRecipeDetailPush.setOnClickListener { view ->
                viewModel.token.observe(viewLifecycleOwner) { token ->
                    if (token != null) {
                        viewModel.pushRecipeToApi(it, token)
                        findNavController().navigate(R.id.recipeLocaleFragment)
                    } else {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }

            binding.btnRecipeDetailEdit.setOnClickListener {
                viewModel.wipeIngredientsItem()
                findNavController().navigate(R.id.recipeEditFragment)
            }

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