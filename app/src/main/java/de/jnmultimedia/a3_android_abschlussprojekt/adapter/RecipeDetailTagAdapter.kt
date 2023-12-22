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
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemTagBinding
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemTagRecipeBinding

class RecipeDetailTagAdapter(
    private var dataset: List<Tag>,
    private val viewModel: MainViewModel,
    private val clickableItems: Boolean,
    private val context: Context
): RecyclerView.Adapter<RecipeDetailTagAdapter.ItemTagRecipeViewHolder>() {

    inner class ItemTagRecipeViewHolder(val binding: ItemTagRecipeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagRecipeViewHolder {
        val binding = ItemTagRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemTagRecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemTagRecipeViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.tvItemTagName.text = item.name

        if (clickableItems) {
            binding.cvItemTag.setOnClickListener {
                viewModel.saveTagItem(item)
                holder.itemView.findNavController()
                    .navigate(R.id.ingredientsSelectionDetailFragment)
            }

            binding.cvItemTag.setOnLongClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Bestätigung")
                    .setMessage("Schlagwort wirklich löschen?")
                    .setPositiveButton("Löschen") { dialog, which ->
                        viewModel.deleteTagInRecipe(item)
                        notifyItemRemoved(item.id!!)
                        Toast
                            .makeText(context, "Schlagwort gelöscht!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("Abbrechen", null)
                    .show()
                true
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Tag>) {
        dataset = list
        notifyDataSetChanged()
    }


}