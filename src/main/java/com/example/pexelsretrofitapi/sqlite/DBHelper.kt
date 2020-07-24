package com.example.sqlitedatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.example.pexelsretrofitapi.model.pexels.Src


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // database
        private val DATABASE_NAME = "pexels_photo.db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "photo_table"

        //colums
        private val COL_ID = "id"
        private val COL_WIDTH = "width"
        private val COL_HEIGHT = "height"
        private val COL_URL = "url"
        private val COL_MEDIUM = "medium"
        private val COL_ORIGINAL = "original"
        private val COL_PORTRAIT = "portrait"
        private val COL_SMALL = "small"
        private val COL_LANDSCAPE = "landscape"
        private val COL_LARGE = "large"
        private val COL_LARGE2X = "large2x"
        private val COL_TINY = "tiny"
        private val COL_PHOTOGRAPHER = "ptg"
        private val COL_PHOTOGRAPHER_ID = "ptgid"
        private val COL_PHOTOGRAPHER_URL = "ptgurl"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER NOT NULL, $COL_WIDTH INTEGER NOT NULL, " +
                    "$COL_HEIGHT INTEGER NOT NULL, $COL_URL TEXT NOT NULL, " +
                    "$COL_PHOTOGRAPHER TEXT NOT NULL, $COL_PHOTOGRAPHER_ID INTEGER NOT NULL, $COL_PHOTOGRAPHER_URL TEXT NOT NULL, " +
                    "$COL_LARGE2X TEXT NOT NULL, $COL_TINY TEXT NOT NULL, " +
                    "$COL_MEDIUM TEXT NOT NULL, $COL_ORIGINAL TEXT NOT NULL, $COL_PORTRAIT TEXT NOT NULL," +
                    "$COL_LANDSCAPE TEXT NOT NULL, $COL_LARGE TEXT NOT NULL, $COL_SMALL TEXT NOT NULL)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun getFavorites(): ArrayList<Photo> {

        val list = arrayListOf<Photo>()
        val db = this.readableDatabase
        val selectquery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectquery, null)

        if (cursor.moveToFirst()) {
            do {
                val height = cursor.getInt(cursor.getColumnIndex(COL_HEIGHT))
                val witdh = cursor.getInt(cursor.getColumnIndex(COL_WIDTH))
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val liked = false
                val url = cursor.getString(cursor.getColumnIndex(COL_URL))
                val photographer = cursor.getString(cursor.getColumnIndex(COL_PHOTOGRAPHER))
                val photographer_id = cursor.getInt(cursor.getColumnIndex(COL_PHOTOGRAPHER_ID))
                val photographer_url = cursor.getString(cursor.getColumnIndex(COL_PHOTOGRAPHER_URL))
                val original = cursor.getString(cursor.getColumnIndex(COL_ORIGINAL))
                val large = cursor.getString(cursor.getColumnIndex(COL_LARGE))
                val large2x = cursor.getString(cursor.getColumnIndex(COL_LARGE2X))
                val medium = cursor.getString(cursor.getColumnIndex(COL_MEDIUM))
                val small = cursor.getString(cursor.getColumnIndex(COL_SMALL))
                val portrait = cursor.getString(cursor.getColumnIndex(COL_PORTRAIT))
                val landscape = cursor.getString(cursor.getColumnIndex(COL_LANDSCAPE))
                val tiny = cursor.getString(cursor.getColumnIndex(COL_TINY))

                val src = Src(landscape, large, large2x, medium, original, portrait, small, tiny)

                val photo = Photo(
                    height, id, liked, photographer,
                    photographer_id, photographer_url, src, url, witdh
                )
                list.add(photo)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun addFavorite(photo: Photo) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COL_ID, photo.id)
        values.put(COL_WIDTH, photo.width)
        values.put(COL_HEIGHT, photo.height)
        values.put(COL_URL, photo.url)
        values.put(COL_PHOTOGRAPHER_ID, photo.photographerId)
        values.put(COL_PHOTOGRAPHER_URL, photo.photographerUrl)
        values.put(COL_PHOTOGRAPHER, photo.photographer)
        values.put(COL_ORIGINAL, photo.src?.original)
        values.put(COL_LARGE, photo.src?.large)
        values.put(COL_LARGE2X, photo.src?.large2x)
        values.put(COL_MEDIUM, photo.src?.medium)
        values.put(COL_SMALL, photo.src?.small)
        values.put(COL_LANDSCAPE, photo.src?.landscape)
        values.put(COL_PORTRAIT, photo.src?.portrait)
        values.put(COL_TINY, photo.src?.tiny)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteFavorite(photo: Photo) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(photo.id.toString()))
        db.close()
    }

}