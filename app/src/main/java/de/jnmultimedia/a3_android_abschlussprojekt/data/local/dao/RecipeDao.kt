package de.jnmultimedia.a3_android_abschlussprojekt.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag

@Dao
interface RecipeDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    // READ
    @Query("SELECT * FROM recipe_table")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipeById(id: Int): Recipe

    // UPDATE
    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("UPDATE recipe_table SET name = :name, description = :description, ingredients = :ingredients, tags = :tags, categories = :categories WHERE id = :recipeId")
    suspend fun updateRecipeById(name: String, description: String, ingredients: List<Ingredient>, tags: List<Tag>, categories: List<Category>, recipeId: Int)

    // DELETE
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe_table WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)

    @Query("DELETE FROM recipe_table")
    suspend fun deleteAllRecipes()
}