package ru.netology.recipes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "authorName")
    val authorName: String,
    @ColumnInfo(name = "recipeName")
    val recipeName: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "share")
    val share: Int = 0,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false,
    @ColumnInfo(name = "cuisine")
    val cuisine: Int
)