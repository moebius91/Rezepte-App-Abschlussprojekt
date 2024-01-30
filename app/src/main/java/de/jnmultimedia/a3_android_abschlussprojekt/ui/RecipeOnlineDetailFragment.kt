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
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeOnlineDetailBinding

class RecipeOnlineDetailFragment: Fragment() {

    private lateinit var binding: FragmentRecipeOnlineDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeOnlineDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recipeItem.observe(viewLifecycleOwner) { it ->
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

            binding.cvBtnRecipeDetailDownload.setOnClickListener { view ->
                viewModel.token.observe(viewLifecycleOwner) { token ->
                    // TODO: Abfrage, ob das entsprechende Rezept bereits in der eigenen Datenbank vorhanden ist
                    //if (token != null) {
                        viewModel.addRecipeToDatabase(it)
                        findNavController().navigate(R.id.recipesLocaleFragment)
                    //} else {
                    //    findNavController().navigate(R.id.loginFragment)
                    //}
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Wir initialisieren eine Variable mit dem NavController
                findNavController().navigateUp()
            }
        })
    }
}