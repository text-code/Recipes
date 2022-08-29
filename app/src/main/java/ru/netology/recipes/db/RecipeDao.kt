package ru.netology.recipes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Query("UPDATE recipes SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    @Query(
        """
            UPDATE recipes SET
            favorite = CASE WHEN favorite THEN 0 ELSE 1 END
            WHERE id = :id
        """
    )
    fun likeByMe(id: Long)

    @Query(
        """
            UPDATE recipes SET
            share = share + 1
            WHERE id = :id
        """
    )
    fun share(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)
}