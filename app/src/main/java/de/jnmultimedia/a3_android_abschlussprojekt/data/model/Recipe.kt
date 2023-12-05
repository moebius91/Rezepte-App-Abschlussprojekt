package de.jnmultimedia.a3_android_abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val tags: List<Tag>,
    val categories: List<Category>,
    val ingredients: List<Ingredient>
)
