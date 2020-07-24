package com.example.pexelsretrofitapi.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table_photo")
class FavoritePhoto : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Int
    @ColumnInfo(name = "photographer")
    var photographer: String
    @ColumnInfo(name = "width")
    var width: Int
    @ColumnInfo(name = "height")
    var height: Int
    @ColumnInfo(name = "original")
    var original: String
    @ColumnInfo(name = "portrait")
    var portrait: String

    constructor(
        id: Int,
        photographer: String,
        width: Int,
        height: Int,
        original: String,
        portrait: String
    ) {
        this.id = id
        this.photographer = photographer
        this.width = width
        this.height = height
        this.original = original
        this.portrait = portrait
    }


}