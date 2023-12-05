package de.jnmultimedia.a3_android_abschlussprojekt.data.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category

interface CategoryDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    // READ
    @Query("SELECT * FROM categories_table")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories_table WHERE id = :id")
    fun getAllCategories(id: Int): LiveData<Category>

    // UPDATE
    @Update
    suspend fun updateCategory(category: Category)

    // DELETE
    @Delete
    suspend fun deleteCategory(category: Category)
}