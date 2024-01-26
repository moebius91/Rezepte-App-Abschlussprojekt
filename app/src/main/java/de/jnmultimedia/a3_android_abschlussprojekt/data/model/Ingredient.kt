package de.jnmultimedia.a3_android_abschlussprojekt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serial

@Entity(tableName = "ingredients_table")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "ingredientId")
    val id: Int? = null,
    val name: String,
    val count: Int? = null,
    val unit: IngredientUnit? = null
)
