package de.jnmultimedia.a3_android_abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
)
