package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.jnmultimedia.a3_android_abschlussprojekt.R
import de.jnmultimedia.a3_android_abschlussprojekt.adapter.TagsSelectionAdapter
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel.MainViewModel
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentTagsSelectionBinding

class TagsSelectionFragment: Fragment() {

    private lateinit var binding: FragmentTagsSelectionBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagsSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TagsSelectionAdapter(listOf(), viewModel)
        binding.rvTagsSelection.adapter = adapter

        viewModel.tags.observe(viewLifecycleOwner) { tagsMainList ->
            val newList = mutableListOf<Tag>()

            tagsMainList.forEach {
                newList.add(it)
            }

            viewModel.selectedTags.observe(viewLifecycleOwner) {
                it.forEach { tag ->
                    newList.removeAll { it.name == tag.name }
                }
            }

            adapter.submitList(newList)
        }

        binding.btnTagSelectionNewTag.setOnClickListener {
            val name = binding.ettTagSelectionNewTagName.text.toString()

            if (name != "") {
                viewModel.addTagToDatabase(Tag(name = name))
                binding.ettTagSelectionNewTagName.text.clear()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //viewModel.wipeAllItems()
                findNavController().navigateUp()
            }
        })
    }
}