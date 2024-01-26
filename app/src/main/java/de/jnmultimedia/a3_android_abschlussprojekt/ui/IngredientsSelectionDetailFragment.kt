package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.IngredientUnit
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentIngredientsSelectionDetailBinding

class IngredientsSelectionDetailFragment: Fragment() {

    private lateinit var binding: FragmentIngredientsSelectionDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIngredientsSelectionDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selection: Int
        var itemId: Int = -1
        var name: String

        viewModel.ingredientItem.observe(viewLifecycleOwner) {
            binding.tvIngrSelectionDetailName.text = it.name
            if (it.count != null) {
                binding.ettIngrSelectionDetailCount.setText(it.count.toString())
            }

            selection = when(it.unit?.name) {
                IngredientUnit.GRAM.name -> 0
                IngredientUnit.KILOGRAM.name -> 1
                IngredientUnit.LITER.name -> 2
                IngredientUnit.MILLILITER.name -> 3
                IngredientUnit.PIECE.name -> 4
                else  -> 0
            }

            if (it.id != null) {
                itemId = it.id
            }

            name = it.name

            binding.spinnerIngrSelectionDetailUnit.setSelection(selection)

            binding.btnIngrSelectionDetailSave.setOnClickListener {
                val count = binding.ettIngrSelectionDetailCount.text.toString()
                val unitString = binding.spinnerIngrSelectionDetailUnit.selectedItem

                val unit = when(unitString) {
                    "g" -> IngredientUnit.GRAM
                    "kg" -> IngredientUnit.KILOGRAM
                    "mL" -> IngredientUnit.MILLILITER
                    "L" -> IngredientUnit.LITER
                    else -> IngredientUnit.PIECE
                }

                val updatedIngredient = Ingredient(
                    itemId,
                    name,
                    count.toInt(),
                    unit
                    )


                viewModel.saveIngredientItem(updatedIngredient)
                viewModel.updateIngredientToRecipe(updatedIngredient)
                findNavController().navigateUp()
            }
        }

        val options = listOf(
            "g",
            "kg",
            "L",
            "mL",
            "Stück",
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerIngrSelectionDetailUnit.adapter = adapter

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })

    }
}