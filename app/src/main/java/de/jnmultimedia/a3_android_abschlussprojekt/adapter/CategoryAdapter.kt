package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var dataset: List<Category>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<CategoryAdapter.ItemCategoryViewHolder>() {

    inner class ItemCategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)

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

        binding.tvItemCategoryName.text = item.name

        binding.cvItemCategory.setOnClickListener {
            viewModel.saveCategoryItem(item)
            //holder.itemView.findNavController().navigate(R.id.categoryDetailFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Category>) {
        dataset = list
        notifyDataSetChanged()
    }


}