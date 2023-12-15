package de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.IngredientRepository
import kotlinx.coroutines.launch

class IngredientViewModel(application: Application) : AndroidViewModel(application) {

    private val database = RecipeDatabase.getDatabase(application)
    private val ingredientRepository = IngredientRepository(database)

    val ingredients = ingredientRepository.ingredients

    fun deleteIngredientInDatabase(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.deleteIngredientInDatabase(ingredient)
        }
    }
}