package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe

class RecipeRepository(
    private val database: RecipeDatabase
) {

    private val REPO_TAG = "REPO"

    val recipes: LiveData<List<Recipe>> = database.recipeDao.getAllRecipes()

    // Datenbank
    suspend fun saveRecipeInDatabase(recipe: Recipe) {
        try {
            database.recipeDao.insertRecipe(recipe)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun updateRecipeInDatabase(recipe: Recipe) {
        try {
            database.recipeDao.updateRecipe(recipe)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteRecipeInDatabase(recipe: Recipe) {
        try {
            database.recipeDao.deleteRecipe(recipe)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteAllRecipesInDatabase() {
        try {
            database.recipeDao.deleteAllRecipes()
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }
}