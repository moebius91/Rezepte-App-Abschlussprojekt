package de.jnmultimedia.a3_android_abschlussprojekt.data.model

data class RecipeCreationRequest(
    val recipe: RecipeOnline,
    val categoryIds: List<Int>,
    val tagIds: List<Int>,
    val ingredientIds: List<Int>
)