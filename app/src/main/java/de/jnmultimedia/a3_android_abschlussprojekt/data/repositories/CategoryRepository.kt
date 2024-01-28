package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category

class CategoryRepository(
    private val database: RecipeDatabase
) {

    private val REPO_TAG = "CategoryRepository"

    val categories: LiveData<List<Category>> = database.categoryDao.getAllCategories()

    // Datenbank
    suspend fun saveCategoryInDatabase(category: Category) {
        try {
            database.categoryDao.insertCategory(category)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun updateCategoryInDatabase(category: Category) {
        try {
            database.categoryDao.updateCategory(category)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteCategoryInDatabase(category: Category) {
        try {
            database.categoryDao.deleteCategory(category)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteAllCategorysInDatabase() {
        try {
            database.categoryDao.deleteAllCategories()
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }
}