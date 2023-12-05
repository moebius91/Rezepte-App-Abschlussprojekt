package de.jnmultimedia.a3_android_abschlussprojekt.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag

@Dao
interface TagDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag)

    // READ
    @Query("SELECT * FROM tags_table")
    fun getAllTags(): LiveData<List<Tag>>

    @Query("SELECT * FROM tags_table WHERE id = :id")
    fun getTagById(id: Int): Tag

    // UPDATE
    @Update
    suspend fun updateTag(tag: Tag)

    // DELETE
    @Delete
    suspend fun deleteTag(tag: Tag)

}