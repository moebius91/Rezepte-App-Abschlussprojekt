package de.jnmultimedia.a3_android_abschlussprojekt.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Category
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Ingredient
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Recipe
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.RecipeCreationRequest
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Tag
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.Token
import de.jnmultimedia.a3_android_abschlussprojekt.data.model.UserCredentials
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val BASE_URL = "https://h2974975.stratoserver.net/"
//const val BASE_URL = "http://10.0.2.2:8080/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RecipesApiService {
    // LOGIN
    @POST("login")
    suspend fun userLogin(@Body userCredentials: UserCredentials): Token

    // CREATE
    @POST("recipe")
    suspend fun createRecipe(@Header("Authorization") authHeader: String, @Body recipeCreationRequest: RecipeCreationRequest): Response<Recipe>

    @POST("ingredient")
    suspend fun createIngredient(@Header("Authorization") authHeader: String, @Body ingredient: Ingredient): Response<Ingredient>

    @POST("tag")
    suspend fun createTag(@Header("Authorization") authHeader: String, @Body tag: Tag): Response<Tag>

    @POST("category")
    suspend fun createCategory(@Header("Authorization") authHeader: String, @Body category: Category): Response<Category>

    // READ
    @GET("recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @GET("recipe/{recipeId}")
    suspend fun getRecipeById(@Path("recipeId") recipeId: Int): Recipe

    @GET("ingredients")
    suspend fun getAllIngredients(): List<Ingredient>

    @GET("tags")
    suspend fun getAllTags(): List<Tag>

    @GET("categories")
    suspend fun getAllCategories(): List<Category>

    // UPDATE
    @PUT("recipe/{recipeId}")
    suspend fun updateRecipeById(@Header("Authorization") authHeader: String, @Path("recipeId") recipeId: Int): Response<Recipe>

    // DELETE
    @DELETE("recipe/{recipeId}")
    suspend fun deleteRecipeById(@Header("Authorization") authHeader: String, @Path("recipeId") recipeId: Int)

}

object RecipesApi {
    val retrofitService: RecipesApiService by lazy { retrofit.create(RecipesApiService::class.java) }
}