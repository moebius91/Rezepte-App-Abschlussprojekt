package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeCreationRequest
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeOnline
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
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
            val ingredientsNew = mutableListOf<Ingredient>()
            getAllIngredients()

            ingredients?.forEach {  ingredient ->
                val newIngredientIndex = ingredientsOnline.value?.indexOfFirst { it.name == ingredient.name }

                if (newIngredientIndex != null) {
                    val newIngredient = ingredientsOnline.value?.get(newIngredientIndex)
                    println("Zutat wurde online gefunden")

                    if (newIngredient != null) {
                        ingredientsNew.add(newIngredient)
                    }
                } else {
                    println("Zutat wird online eingetragen")
                    // TODO: Eigene createTag Funktion draus machen.
                    val newIngredientResponse = apiService.retrofitService.createIngredient(
                        authHeader = bearer,
                        Ingredient(
                            name = ingredient.name,
                        )
                    )

                    val newIngredientFromResponse = newIngredientResponse.body()

                    if (newIngredientFromResponse != null) {
                        val newIngredient = Ingredient(
                            newIngredientFromResponse.id,
                            newIngredientFromResponse.name,
                            ingredient.count,
                            ingredient.unit
                        )

                        if (newIngredient.id != null) {
                            ingredientsNew.add(newIngredient)
                        }
                    }
                }
            }

            apiService.retrofitService.createRecipe(
                authHeader = bearer,
                RecipeCreationRequest(
                    recipeOnline,
                    listOf(),
                    listOf(),
                    listOf(),
                    //ingredientsNew
                )
            )
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