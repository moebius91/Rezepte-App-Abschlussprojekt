package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.remote.RecipesApi

class ApiRepository(
    private val apiService: RecipesApi
) {
    private val REPO_TAG = "ApiRepository"

    private var _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    suspend fun getAllRecipes() {
        try {
            val result = apiService.retrofitService.getAllRecipes()
            _recipes.postValue(result)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }



}