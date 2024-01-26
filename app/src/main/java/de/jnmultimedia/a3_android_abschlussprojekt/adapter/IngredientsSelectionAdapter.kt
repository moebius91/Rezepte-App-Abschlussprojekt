package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemIngredientBinding

class IngredientsSelectionAdapter(
    private var dataset: List<Ingredient>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<IngredientsSelectionAdapter.ItemIngredientViewHolder>() {

    inner class ItemIngredientViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root) {
        var ingredientActive = false
    }

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

        binding.tvIngredientCount.visibility = View.GONE
        binding.tvIngredientUnit.visibility = View.GONE

        binding.cvItemIngredient.visibility = if (holder.ingredientActive) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.cvItemIngredient.setOnClickListener {
            if (holder.ingredientActive) {
                holder.ingredientActive = false
            } else {
                holder.ingredientActive = true
            }
            viewModel.addIngredientToRecipe(item)
            viewModel.outOfIngredientsSelection()
            holder.itemView.findNavController().navigateUp()
        }
        binding.tvIngredientName.text = item.name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Ingredient>) {
        dataset = list
        notifyDataSetChanged()
    }


}