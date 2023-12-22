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

class RecipeOnlineAdapter(
    private var dataset: List<Recipe>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<RecipeOnlineAdapter.ItemRecipeOnlineViewHolder>() {

    inner class ItemRecipeOnlineViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecipeOnlineViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemRecipeOnlineViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemRecipeOnlineViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.tvItemRecipeName.text = item.name

        binding.cvItemRecipe.setOnClickListener {
            viewModel.saveRecipeItem(item)
            holder.itemView.findNavController().navigate(R.id.recipeOnlineDetailFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Recipe>) {
        dataset = list
        notifyDataSetChanged()
    }


}