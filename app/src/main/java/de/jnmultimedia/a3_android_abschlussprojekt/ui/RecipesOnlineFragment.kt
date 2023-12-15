package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.RecipeAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipesOnlineBinding

class RecipesOnlineFragment: Fragment() {

    private lateinit var binding: FragmentRecipesOnlineBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesOnlineBinding.inflate(layoutInflater)
        viewModel.getAllRecipesFromApi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeAdapter(listOf(), viewModel)
        binding.rvRecipes.adapter = adapter

        viewModel.recipesOnline.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}