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
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Token
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.UserCredentials
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
    val token = apiRepository.token

    val ingredientsOnline = apiRepository.ingredientsOnline

    private var _recipeItem = MutableLiveData<Recipe>()
    val recipeItem: LiveData<Recipe>
        get() = _recipeItem

    private var _selectedIngredients = MutableLiveData<MutableList<Ingredient>>(mutableListOf())
    val selectedIngredients: LiveData<MutableList<Ingredient>>
        get() = _selectedIngredients

    private var _selectedTags = MutableLiveData<MutableList<Tag>>(mutableListOf())
    val selectedTags: LiveData<MutableList<Tag>>
        get() = _selectedTags

    private var _selectedCategories = MutableLiveData<MutableList<Category>>(mutableListOf())
    val selectedCategories: LiveData<MutableList<Category>>
        get() = _selectedCategories

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

    private var _inEditProcess = MutableLiveData(false)
    val inEditProcess: LiveData<Boolean>
        get() = _inEditProcess

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

    fun updateRecipeByIdInDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.updateRecipeByIdInDatabase(recipe)
        }
    }

    fun deleteRecipeInDatabase(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeInDatabase(recipe)
        }
    }

    fun deleteRecipeByIdInDatabase(recipeId: Int) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeByIdInDatabase(recipeId)
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
        _selectedTags.postValue(mutableListOf())
        _selectedCategories.postValue(mutableListOf())
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

    fun saveSelectedIngredients(ingredients: MutableList<Ingredient>) {
        _selectedIngredients.postValue(ingredients)
    }

    fun addIngredientToRecipe(ingredient: Ingredient) {
        _selectedIngredients.value?.add(ingredient)
    }

    fun updateIngredientToRecipe(ingredient: Ingredient) {
        _selectedIngredients.value?.removeIf { it.id == ingredient.id }
        _selectedIngredients.value?.add(ingredient)
    }

    fun deleteIngredientInRecipe(ingredient: Ingredient) {
        _selectedIngredients.value?.removeIf { it.id == ingredient.id }
    }

    fun isIngredientInRecipe(ingredient: Ingredient): Boolean {
        return _selectedIngredients.value?.contains(ingredient) ?: false
    }

    fun wipeIngredientsItem() {
        _selectedIngredients.postValue(mutableListOf())
    }

    fun deleteIngredientInDatabase(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.deleteIngredientInDatabase(ingredient)
        }
    }

    fun inIngredientsSelection() {
        _inIngredientSelection.postValue(true)
    }

    fun outOfIngredientsSelection() {
        _inIngredientSelection.postValue(false)
    }

    fun addTagToDatabase(tag: Tag) {
        viewModelScope.launch {
            tagRepository.saveTagInDatabase(tag)
        }
    }

    fun addTagToRecipe(tag: Tag) {
        _selectedTags.value?.add(tag)
    }

    fun updateTagToRecipe(tag: Tag) {
        _selectedTags.value?.removeIf { it.id == tag.id }
        _selectedTags.value?.add(tag)
    }

    fun deleteTagInRecipe(tag: Tag) {
        val newList: MutableList<Tag>? = _selectedTags.value
        newList?.removeIf { it.id == tag.id }

        if (newList != null) {
            _selectedTags.postValue(newList!!)
        } else {
            _selectedTags.postValue(mutableListOf())
        }

        //_selectedTags.value?.removeIf { it.id == tag.id }
    }

    fun addCategoryToDatabase(category: Category) {
        viewModelScope.launch {
            categoryRepository.saveCategoryInDatabase(category)
        }
    }

    fun saveSelectedCategories(categories: MutableList<Category>) {
        _selectedCategories.postValue(categories)
    }

    fun addCategoryToRecipe(category: Category) {
        _selectedCategories.value?.add(category)
    }

    fun updateCategoryToRecipe(category: Category) {
        _selectedCategories.value?.removeIf { it.id == category.id }
        _selectedCategories.value?.add(category)
    }

    fun deleteCategoryInRecipe(category: Category) {
        _selectedCategories.value?.removeIf { it.id == category.id }
    }

    fun saveTagItem(tag: Tag) {
        _tagItem.postValue(tag)
    }

    fun saveSelectedTags(tags: MutableList<Tag>) {
        _selectedTags.postValue(tags)
    }

    fun saveCategoryItem(category: Category) {
        _categoryItem.postValue(category)
    }

    fun getAllRecipesFromApi() {
        viewModelScope.launch {
            apiRepository.getAllRecipes()
        }
    }

    fun setInEditProcess() {
        _inEditProcess.postValue(true)
    }

    fun disableInEditProcess() {
        _inEditProcess.postValue(false)
    }


    // API Abschnitt

    fun loginToApi(userCredentials: UserCredentials, callback: (Boolean) -> Unit) {
        viewModelScope.launch{
            val success = apiRepository.loginToApi(userCredentials)
            callback(success)
        }
    }

    fun pushRecipeToApi(recipe: Recipe, token: Token) {
        viewModelScope.launch {
            apiRepository.createRecipe(recipe, token)
        }
    }

}