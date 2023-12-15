package de.jnmultimedia.a3_android_abschlussprojekt.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient

@Dao
interface IngredientDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    // READ
    @Query("SELECT * FROM ingredients_table ORDER BY name ASC")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM categories_table WHERE id = :id")
    fun getIngredientById(id: Int): LiveData<Ingredient>

    // UPDATE
    @Update
    suspend fun updateIngredient(ingredient: Ingredient)

    // DELETE
    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("DELETE FROM tags_table")
    suspend fun deleteAllIngredients()
}