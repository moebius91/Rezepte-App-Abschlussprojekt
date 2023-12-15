package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient

class IngredientRepository(
    private val database: RecipeDatabase
) {

    private val REPO_TAG = "REPO"

    val ingredients: LiveData<List<Ingredient>> = database.ingredientDao.getAllIngredients()

    // Datenbank
    suspend fun saveIngredientInDatabase(ingredient: Ingredient) {
        try {
            database.ingredientDao.insertIngredient(ingredient)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun updateIngredientInDatabase(ingredient: Ingredient) {
        try {
            database.ingredientDao.updateIngredient(ingredient)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteIngredientInDatabase(ingredient: Ingredient) {
        try {
            database.ingredientDao.deleteIngredient(ingredient)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteAllIngredientsInDatabase() {
        try {
            database.ingredientDao.deleteAllIngredients()
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }
}