package com.example.pexelsretrofitapi.room

import androidx.room.*

@Dao
interface FavoritePhotoDAO {

    @Query("SELECT * FROM table_photo")
    fun getAllPhoto(): List<FavoritePhoto>

    @Insert
    fun inSertPhoto(photo: FavoritePhoto)

    @Delete
    fun deletePhoto(photo: FavoritePhoto)

    @Update
    fun updatePhoto(photo: FavoritePhoto)
}