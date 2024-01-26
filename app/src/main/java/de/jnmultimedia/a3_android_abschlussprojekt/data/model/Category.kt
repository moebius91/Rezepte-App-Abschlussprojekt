package de.jnmultimedia.a3_android_abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "categoryId")
    val id: Int? = null,
    val name: String
)
