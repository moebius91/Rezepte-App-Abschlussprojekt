package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.IngredientsUnit
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemIngredientBinding
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemRecipeBinding

class RecipeEditIngredientAdapter(
    private var dataset: List<Ingredient>,
    private val viewModel: MainViewModel,
    private val clickableItems: Boolean
): RecyclerView.Adapter<RecipeEditIngredientAdapter.ItemIngredientViewHolder>() {

    inner class ItemIngredientViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemIngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemIngredientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemIngredientViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.tvIngredientCount.visibility = if (item.count == null) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.tvIngredientUnit.visibility = if (item.unit == null) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.tvIngredientName.text = item.name
        binding.tvIngredientCount.text = if (item.count != null) item.count.toString() else ""
        binding.tvIngredientUnit.text = when (item.unit?.name) {
            IngredientsUnit.GRAM.name -> "g"
            IngredientsUnit.KILOGRAM.name -> "kg"
            IngredientsUnit.LITER.name -> "L"
            IngredientsUnit.MILLILITER.name -> "mL"
            IngredientsUnit.PIECE.name -> "Stück"
            else -> ""
        }

        if (clickableItems) {
            binding.cvItemIngredient.setOnClickListener {
                viewModel.saveIngredientItem(item)
                holder.itemView.findNavController()
                    .navigate(R.id.ingredientsSelectionDetailFragment)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Ingredient>) {
        dataset = list
        notifyDataSetChanged()
    }


}