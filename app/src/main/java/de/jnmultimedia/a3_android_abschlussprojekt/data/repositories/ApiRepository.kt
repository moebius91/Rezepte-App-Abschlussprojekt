package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeCreationRequest
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeOnline
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Token
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.UserCredentials
import de.jnmultimedia.a3_android_abschlussprojekt.data.remote.RecipesApi

class ApiRepository(
    private val apiService: RecipesApi
) {
    private val REPO_TAG = "ApiRepository"

    private var _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    private var _token = MutableLiveData<Token>(null)
    val token: LiveData<Token>
        get() = _token

    private var _ingredientsOnline = MutableLiveData<MutableList<Ingredient>>(null)
    val ingredientsOnline: LiveData<MutableList<Ingredient>>
        get() = _ingredientsOnline

    suspend fun createRecipe(recipe: Recipe, token: Token) {
        try {
            val bearer = "Bearer ${token.token}"

            val recipeOnline = RecipeOnline(
                name = recipe.name,
                description = recipe.description
            )

            val ingredients = recipe.ingredients
            val ingredientIds = mutableListOf<Int>()
            getAllIngredients()

            ingredients?.forEach {  ingredient ->
                val newIngredient = ingredientsOnline.value?.indexOfFirst { it.name == ingredient.name }

                if (newIngredient != null) {
                    ingredientIds.add(newIngredient)
                } else {

                }
            }

            apiService.retrofitService.createRecipe(
                authHeader = bearer,
                RecipeCreationRequest(
                    recipeOnline,
                    listOf(),
                    listOf(),
                    ingredientIds
                )
            )
            println(bearer)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun loginToApi(userCredentials: UserCredentials): Boolean {
        var success: Boolean
        try {
            val token = apiService.retrofitService.userLogin(userCredentials)
            _token.postValue(token)
            success = true
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
            success = false
        }
        return success
    }

    suspend fun getAllRecipes() {
        try {
            val result = apiService.retrofitService.getAllRecipes()
            _recipes.postValue(result)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

    suspend fun getAllIngredients() {
        try {
            val result = apiService.retrofitService.getAllIngredients().toMutableList()
            _ingredientsOnline.postValue(result)
        } catch (e: Exception) {
            Log.e(REPO_TAG, e.message.toString())
        }
    }

}