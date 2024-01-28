package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag

class TagRepository(
    private val database: RecipeDatabase
) {

    private val REPO_TAG = "TagRepository"

    val tags: LiveData<List<Tag>> = database.tagDao.getAllTags()

    // Datenbank
    suspend fun saveTagInDatabase(tag: Tag) {
        try {
            database.tagDao.insertTag(tag)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun updateTagInDatabase(tag: Tag) {
        try {
            database.tagDao.updateTag(tag)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteTagInDatabase(tag: Tag) {
        try {
            database.tagDao.deleteTag(tag)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun deleteAllTagsInDatabase() {
        try {
            database.tagDao.deleteAllTags()
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }
}