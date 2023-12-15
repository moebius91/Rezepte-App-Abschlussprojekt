package de.jnmultimedia.a3_android_abschlussprojekt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.ItemTagBinding

class TagAdapter(
    private var dataset: List<Tag>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<TagAdapter.ItemTagViewHolder>() {

    inner class ItemTagViewHolder(val binding: ItemTagBinding): RecyclerView.ViewHolder(binding.root)

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

        binding.tvItemTagName.text = item.name

        binding.cvItemTag.setOnClickListener {
            viewModel.saveTagItem(item)
            //holder.itemView.findNavController().navigate(R.id.tagDetailFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Tag>) {
        dataset = list
        notifyDataSetChanged()
    }


}