package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemRvIngredientsBinding

class IngredientAdapter(
    val context: Context,
    private var dataset: List<Ingredient>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<IngredientAdapter.ItemIngredientViewHolder>() {

    inner class ItemIngredientViewHolder(val binding: ItemRvIngredientsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemIngredientViewHolder {
        val binding = ItemRvIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemIngredientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemIngredientViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.tvIngredientName.text = item.name

        binding.cvItemIngredient.setOnLongClickListener {
            // TODO: Abfrage, ob Zutat in einem Rezept genutzt wird.

            if (true)

            AlertDialog.Builder(context)
                .setTitle("Bestätigung")
                .setMessage("Zutat wirklich löschen?")
                .setPositiveButton("Löschen") { dialog, which ->
                    viewModel.deleteIngredientInDatabase(item)
                    Toast
                        .makeText(context, "Zutat gelöscht!", Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("Abbrechen", null)
                .show()
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Ingredient>) {
        dataset = list
        notifyDataSetChanged()
    }


}