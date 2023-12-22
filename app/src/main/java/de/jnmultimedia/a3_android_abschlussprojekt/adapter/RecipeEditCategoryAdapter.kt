package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemCategoryBinding

class RecipeEditCategoryAdapter(
    private var dataset: List<Category>,
    private val viewModel: MainViewModel,
    private val clickableItems: Boolean,
    private val context: Context
): RecyclerView.Adapter<RecipeEditCategoryAdapter.ItemCategoryViewHolder>() {

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

        if (clickableItems) {
            binding.cvItemCategory.setOnClickListener {
                viewModel.saveCategoryItem(item)
                holder.itemView.findNavController()
                    .navigate(R.id.ingredientsSelectionDetailFragment)
            }


            binding.cvItemCategory.setOnLongClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Bestätigung")
                    .setMessage("Kategorie wirklich löschen?")
                    .setPositiveButton("Löschen") { dialog, which ->
                        viewModel.deleteCategoryInRecipe(item)
                        notifyItemRemoved(item.id!!)
                        Toast
                            .makeText(context, "Kategorie gelöscht!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Abbrechen", null)
                    .show()
                true
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Category>) {
        dataset = list
        notifyDataSetChanged()
    }


}