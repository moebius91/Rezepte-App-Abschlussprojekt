package de.jnmultimedia.a3_android_abschlussprojekt.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.RecipeDatabase
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.remote.RecipesApi
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.ApiRepository
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.CategoryRepository
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.IngredientRepository
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.RecipeRepository
import de.jnmultimedia.a3_android_abschlussprojekt.data.repositories.TagRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = RecipeDatabase.getDatabase(application)
    private val recipeRepository = RecipeRepository(database)
    private val ingredientRepository = IngredientRepository(database)
    private val tagRepository = TagRepository(database)
    private val categoryRepository = CategoryRepository(database)
    private val apiRepository = ApiRepository(RecipesApi)

    val recipes = recipeRepository.recipes
    val recipesOnline = apiRepository.recipes
    val ingredients = ingredientRepository.ingredients
    val tags = tagRepository.tags
    val categories = categoryRepository.categories

    private var _recipeItem = MutableLiveData<Recipe>()
    val recipeItem: LiveData<Recipe>
        get() = _recipeItem

    private var _selectedIngredients = MutableLiveData<MutableList<Ingredient>>(mutableListOf())
    val selectedIngredients: LiveData<MutableList<Ingredient>>
        get() = _selectedIngredients

    private var _ingredientItem = MutableLiveData<Ingredient>()
    val ingredientItem: LiveData<Ingredient>
        get() = _ingredientItem

    private var _tagItem = MutableLiveData<Tag>()
    val tagItem: LiveData<Tag>
        get() = _tagItem

    private var _categoryItem = MutableLiveData<Category>()
    val categoryItem: LiveData<Category>
        get() = _categoryItem

    private var _inIngredientSelection = MutableLiveData(false)
    val inIngredientSelection: LiveData<Boolean>
        get() = _inIngredientSelection

    private var _recipeExists = MutableLiveData(false)
    val recipeExists: LiveData<Boolean>
        get() = _recipeExists

    fun addRecipeToDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.saveRecipeInDatabase(recipe)
        }
    }

    fun recipeExistsInDatabase(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            _recipeExists.postValue(recipe != null)
        }
    }

    fun updateRecipeInDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.updateRecipeInDatabase(recipe)
        }
    }

    fun deleteRecipeInDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeInDatabase(recipe)
        }
    }

    fun deleteAllRecipesInDatabase() {
        viewModelScope.launch {
            recipeRepository.deleteAllRecipesInDatabase()
        }
    }

    fun saveRecipeItem(recipe: Recipe) {
        _recipeItem.postValue(recipe)
    }

    fun wipeAllItems() {
        _recipeItem = MutableLiveData()
        _selectedIngredients = MutableLiveData<MutableList<Ingredient>>(mutableListOf())
        _ingredientItem = MutableLiveData()
    }

    fun wipeAllIngredientItems() {
        _selectedIngredients = MutableLiveData<MutableList<Ingredient>>(mutableListOf())
        _ingredientItem = MutableLiveData()
    }

    fun saveIngredientItem(ingredient: Ingredient) {
        _ingredientItem.postValue(ingredient)
    }

    fun addIngredientToDatabase(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.saveIngredientInDatabase(ingredient)
        }
    }

    fun addIngredientToRecipe(ingredient: Ingredient) {
        _selectedIngredients.value?.add(ingredient)
    }

    fun isIngredientInRecipe(ingredient: Ingredient): Boolean {
        return _selectedIngredients.value?.contains(ingredient) ?: false
    }

    fun removeIngredientFromRecipe(ingredient: Ingredient) {
        _selectedIngredients.value?.remove(ingredient)
    }

    fun wipeIngredientsItem() {
        _selectedIngredients = MutableLiveData<MutableList<Ingredient>>()
    }

    fun inIngredientsSelection() {
        _inIngredientSelection.postValue(true)
    }

    fun outOfIngredientsSelection() {
        _inIngredientSelection.postValue(false)
    }

    fun saveTagItem(tag: Tag) {
        _tagItem.postValue(tag)
    }

    fun saveCategoryItem(category: Category) {
        _categoryItem.postValue(category)
    }

    fun getAllRecipesFromApi() {
        viewModelScope.launch {
            apiRepository.getAllRecipes()
        }
    }

}