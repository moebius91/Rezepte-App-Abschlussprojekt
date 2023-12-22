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

    fun getRecipeById(recipeId: Int): Recipe? {
        try {
            return database.recipeDao.getRecipeById(recipeId)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
            return null
        }
    }

    suspend fun updateRecipeInDatabase(recipe: Recipe) {
        try {
            database.recipeDao.updateRecipe(recipe)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun updateRecipeByIdInDatabase(recipe: Recipe) {
        try {
            val name = recipe.name
            val description = recipe.description
            val ingredients = recipe.ingredients
            val tags = recipe.tags
            val categories = recipe.categories

            database.recipeDao.updateRecipeById(name, description, ingredients!!, tags!!, categories!!, recipe.id!!)
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

    suspend fun deleteRecipeByIdInDatabase(recipeId: Int) {
        try {
            database.recipeDao.deleteRecipeById(recipeId)
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