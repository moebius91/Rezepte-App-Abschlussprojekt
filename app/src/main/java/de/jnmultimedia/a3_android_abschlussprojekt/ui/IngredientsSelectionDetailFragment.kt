package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.IngredientsUnit
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

        viewModel.ingredientItem.observe(viewLifecycleOwner) {
            binding.tvIngrSelectionDetailName.text = it.name
        }

        binding.btnIngrSelectionDetailSave.setOnClickListener {
            val count = binding.ettIngrSelectionDetailCount.text.toString()
            val unit = binding.spinnerIngrSelectionDetailUnit

            val options = listOf(
                IngredientsUnit.GRAM.name,
                IngredientsUnit.KILOGRAM.name,
                IngredientsUnit.LITER.name,
                IngredientsUnit.MILLILITER.name,
                IngredientsUnit.PIECE.name,
            )

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerIngrSelectionDetailUnit.adapter = adapter
            findNavController().navigateUp()
        }
    }
}