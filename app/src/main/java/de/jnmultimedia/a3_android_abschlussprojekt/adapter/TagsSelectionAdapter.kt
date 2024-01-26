package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemTagBinding

class TagsSelectionAdapter(
    private var dataset: List<Tag>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<TagsSelectionAdapter.ItemTagViewHolder>() {

    inner class ItemTagViewHolder(val binding: ItemTagBinding): RecyclerView.ViewHolder(binding.root) {
        var ingredientActive = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemTagViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.cvItemTag.setOnClickListener {
            if (holder.ingredientActive) {
                holder.ingredientActive = false
            } else {
                holder.ingredientActive = true
            }
            viewModel.addTagToRecipe(item)
            viewModel.outOfIngredientsSelection()
            holder.itemView.findNavController().navigateUp()
        }

        binding.tvItemTagName.text = item.name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Tag>) {
        dataset = list
        notifyDataSetChanged()
    }


}