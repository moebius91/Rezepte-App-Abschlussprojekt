package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemRecipeBinding

class RecipeAdapter(
    private var dataset: List<Recipe>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<RecipeAdapter.ItemRecipeViewHolder>() {

    inner class ItemRecipeViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemRecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemRecipeViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.tvItemRecipeName.text = item.name

        binding.cvItemRecipe.setOnClickListener {
            viewModel.saveRecipeItem(item)
            holder.itemView.findNavController().navigate(R.id.recipeDetailFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Recipe>) {
        dataset = list
        notifyDataSetChanged()
    }


}