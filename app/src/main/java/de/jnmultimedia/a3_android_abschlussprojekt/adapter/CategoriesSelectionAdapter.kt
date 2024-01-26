package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemCategoryBinding

class CategoriesSelectionAdapter(
    private var dataset: List<Category>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<CategoriesSelectionAdapter.ItemCategoryViewHolder>() {

    inner class ItemCategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        var ingredientActive = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.cvItemCategory.setOnClickListener {
            if (holder.ingredientActive) {
                holder.ingredientActive = false
            } else {
                holder.ingredientActive = true
            }
            viewModel.addCategoryToRecipe(item)
            viewModel.outOfIngredientsSelection()
            holder.itemView.findNavController().navigateUp()
        }


        binding.tvItemCategoryName.text = item.name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Category>) {
        dataset = list
        notifyDataSetChanged()
    }


}