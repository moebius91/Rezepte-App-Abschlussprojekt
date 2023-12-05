package de.jnmultimedia.a3_android_abschlussprojekt.data.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient

interface IngredientDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    // READ
    @Query("SELECT * FROM ingredients_table")
    fun getAllCategories(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM categories_table WHERE id = :id")
    fun getAllCategories(id: Int): LiveData<Ingredient>

    // UPDATE
    @Update
    suspend fun updateIngredient(ingredient: Ingredient)

    // DELETE
    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)
}