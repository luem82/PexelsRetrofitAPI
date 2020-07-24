package com.example.pexelsretrofitapi.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoritePhoto::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDatabase(): FavoritePhotoDAO
}