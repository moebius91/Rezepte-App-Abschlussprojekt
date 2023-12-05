package de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.RecipeRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = RecipeDatabase.getDatabase(application)
    private val recipeRepository = RecipeRepository(database)

    val recipes = recipeRepository.recipes

    fun addRecipeToDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.saveRecipeInDatabase(recipe)
        }
    }

    fun deleteAllRecipesInDatabase() {
        viewModelScope.launch {
            recipeRepository.deleteAllRecipesInDatabase()
        }
    }

}