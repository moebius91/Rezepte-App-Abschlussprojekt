package de.jnmultimedia.a3_android_abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_table")
data class IngredientOnline(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val count: Int? = null,
    val unit: String? = null
)
