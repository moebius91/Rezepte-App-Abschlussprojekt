package de.jnmultimedia.a3_android_abschlussprojekt.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag

class Converters {

    // Die Logik habe ich mir von ChatGPT zeigen lassen
    @TypeConverter
    fun fromTagsList(tags: List<Tag>): String {
        // Erstellt ein Gson-Objekt
        val gson = Gson()
        // Nutzt die Methode toJson mit der Tagsliste
        return gson.toJson(tags)
    }

    @TypeConverter
    fun toTagsList(tagsString: String): List<Tag> {
        val listType = object : TypeToken<List<Tag>>() {}.type
        return Gson().fromJson(tagsString, listType)
    }
    @TypeConverter
    fun fromIngredientList(ingredients: List<Ingredient>): String {
        val gson = Gson()
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun toIngredientList(ingredientsString: String): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(ingredientsString, listType)
    }
    @TypeConverter
    fun fromCategoriesList(categories: List<Category>): String {
        val gson = Gson()
        return gson.toJson(categories)
    }

    @TypeConverter
    fun toCategoriesList(categoriesString: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(categoriesString, listType)
    }
}