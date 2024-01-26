package de.jnmultimedia.a3_android_abschlussprojekt.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeCreationRequest
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeOnline
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Token
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.UserCredentials
import de.jnmultimedia.a3_android_abschlussprojekt.data.remote.RecipesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

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

    private var _tagsOnline = MutableLiveData<MutableList<Tag>>(null)
    val tagsOnline: LiveData<MutableList<Tag>>
        get() = _tagsOnline

    private var _categoriesOnline = MutableLiveData<MutableList<Category>>(null)
    val categoriesOnline: LiveData<MutableList<Category>>
        get() = _categoriesOnline

    suspend fun createRecipe(recipe: Recipe, token: Token) {
        try {
            val bearer = "Bearer ${token.token}"

            val recipeOnline = RecipeOnline(
                name = recipe.name,
                description = recipe.description
            )

            // Zutatenverarbeitung für die API
            val ingredients = recipe.ingredients
            val ingredientsNew = mutableListOf<Ingredient>()
            getAllIngredients().await()
            ingredients?.forEach {  ingredient ->
                val newIngredientIndex = ingredientsOnline.value?.indexOfFirst { it.name == ingredient.name }

                if (newIngredientIndex != -1 && newIngredientIndex != null) {
                    val newIngredient = ingredientsOnline.value?.get(newIngredientIndex)
                    println("Zutat wurde online gefunden")

                    if (newIngredient != null) {
                        ingredientsNew.add(newIngredient)
                    }
                } else {
                    println("Zutat wird online eingetragen")
                    val newIngredientResponse = apiService.retrofitService.createIngredient(
                        authHeader = bearer,
                        Ingredient(
                            name = ingredient.name,
                        )
                    )

                    getAllIngredients().await()
                    val createdIngredientIndex = ingredientsOnline.value?.indexOfFirst { it.name == ingredient.name }
                    val newIngredientId = ingredientsOnline.value?.get(createdIngredientIndex!!)?.id

                    val newIngredientFromResponse = newIngredientResponse.body()

                    if (newIngredientFromResponse != null) {
                        val newIngredient = Ingredient(
                            newIngredientId,
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

            // Schlagwortverarbeitung für die API
            val tags = recipe.tags
            val tagsIds = mutableListOf<Int>()
            getAllTags().await()
            tags?.forEach {  tag ->
                val newTagIndex = tagsOnline.value?.indexOfFirst { it.name == tag.name }

                if (newTagIndex != -1 && newTagIndex != null) {
                    val newTagId = tagsOnline.value?.get(newTagIndex!!)?.id

                    if (newTagId != null) {
                        tagsIds.add(newTagId)
                    }
                } else {
                    apiService.retrofitService.createTag(
                        authHeader = bearer,
                        Tag(
                            name = tag.name,
                        )
                    )

                    getAllTags().await()
                    val createdTagIndex = tagsOnline.value?.indexOfFirst { it.name == tag.name }
                    val newTagId = tagsOnline.value?.get(createdTagIndex!!)?.id

                    if (newTagId != null) {
                        tagsIds.add(newTagId)
                    }
                }
            }

            // Schlagwortverarbeitung für die API
            val categories = recipe.categories
            val categoriesIds = mutableListOf<Int>()
            getAllCategories().await()
            categories?.forEach {  category ->
                println(category)
                println("\n")
                val newCategoryIndex = categoriesOnline.value?.indexOfFirst { it.name == category.name }

                if (newCategoryIndex != -1 && newCategoryIndex != null) {
                    val newCategoryId = categoriesOnline.value?.get(newCategoryIndex)?.id

                    if (newCategoryId != null) {
                        categoriesIds.add(newCategoryId)
                    }
                } else {
                    apiService.retrofitService.createCategory(
                        authHeader = bearer,
                        Category(
                            name = category.name,
                        )
                    )

                    getAllCategories().await()
                    val createdCategoryIndex = categoriesOnline.value?.indexOfFirst { it.name == category.name }
                    println(createdCategoryIndex.toString() + "\n\n")
                    val newCategoryId = categoriesOnline.value?.get(createdCategoryIndex!!)?.id

                    if (newCategoryId != null) {
                        categoriesIds.add(newCategoryId)
                    }
                }
            }
            apiService.retrofitService.createRecipe(
                authHeader = bearer,
                RecipeCreationRequest(
                    recipeOnline,
                    categoriesIds,
                    tagsIds,
                    ingredientsNew
                )
            )
        } catch (e: Exception) {
            Log.e(REPO_TAG +"Tags", e.message.toString())
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

    suspend fun getAllIngredients(): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val result = apiService.retrofitService.getAllIngredients().toMutableList()
                _ingredientsOnline.postValue(result)
            } catch (e: Exception) {
                Log.e(REPO_TAG, e.message.toString())
            }
        }
    }

    suspend fun getAllTags(): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val result = apiService.retrofitService.getAllTags().toMutableList()
                _tagsOnline.postValue(result)
            } catch (e: Exception) {
                Log.e(REPO_TAG, e.message.toString())
            }
        }
    }

    suspend fun getAllCategories(): Deferred<Unit> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val result = apiService.retrofitService.getAllCategories().toMutableList()
                _categoriesOnline.postValue(result)
            } catch (e: Exception) {
                Log.e(REPO_TAG, e.message.toString())
            }
        }
    }

}