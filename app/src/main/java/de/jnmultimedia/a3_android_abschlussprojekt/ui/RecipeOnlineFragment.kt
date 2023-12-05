package de.jnmultimedia.a3_android_abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.jnmultimedia.a3_android_abschlussprojekt.databinding.FragmentRecipeOnlineBinding

class RecipeOnlineFragment: Fragment() {

    private lateinit var binding: FragmentRecipeOnlineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeOnlineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}