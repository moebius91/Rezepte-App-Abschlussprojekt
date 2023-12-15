package de.jnmultimedia.a3_android_abschlussprojekt.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.dao.CategoryDao
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.dao.IngredientDao
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.dao.RecipeDao
import de.jnmultimedia.a3_android_abschlussprojekt.data.local.dao.TagDao
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag

@Database(entities = [Recipe::class, Tag::class, Category::class, Ingredient::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao
    abstract val ingredientDao: IngredientDao
    abstract val tagDao: TagDao
    abstract val categoryDao: CategoryDao

    companion object {
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}