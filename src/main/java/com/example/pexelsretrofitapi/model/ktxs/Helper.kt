package com.example.pexelsretrofitapi.model.ktxs

import android.content.Context
import android.widget.Toast
import com.example.pexelsretrofitapi.network.ApiClient
import com.example.pexelsretrofitapi.network.ApiService
import android.app.Activity
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.example.pexelsretrofitapi.room.FavoritePhoto
import com.example.pexelsretrofitapi.ui.MainActivity
import com.example.pexelsretrofitapi.ui.SearchActivity
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList


object Helper {

    fun downloadPhoto(photoModel: Photo, context: Context) {

        var downloadUri = Uri.parse(photoModel.src?.original!!)
        var fileName = "${photoModel.photographer.replace(" ", "_")}_${photoModel.id}.jpg"
        val photoDirPath = Environment.getExternalStorageDirectory().toString() + "/Pexels"
        val photoDir = File(photoDirPath)
        if (photoDir.exists().not()) photoDir.mkdirs()
        val photoFile = File(photoDir, fileName)

        val downloadRequest = DownloadManager.Request(downloadUri)
            .setTitle(fileName)
            .setDescription("downloading photo")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(photoFile))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)


        val downloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(downloadRequest)

    }


    fun setAsWallpaper(photoModel: Photo, context: Context, mCustomBottomSheet: LinearLayout) {

        var asyncTask = object : AsyncTask<String, Void, Bitmap>() {

            override fun doInBackground(vararg params: String?): Bitmap? {
                try {
                    val url = URL(params[0])
                    val connection = url.openConnection() as HttpURLConnection
                    connection.setDoInput(true)
                    connection.connect()
                    val input = connection.getInputStream()
                    val myBitmap = BitmapFactory.decodeStream(input)
                    return myBitmap
                } catch (e: IOException) {
                    System.out.println(e)
                }
                return null
            }

            override fun onPostExecute(result: Bitmap?) {
                super.onPostExecute(result)
                var wallpaper = WallpaperManager.getInstance(context)
                wallpaper.setBitmap(result)
                Snackbar.make(
                    mCustomBottomSheet,
                    "Set as wallpaper done",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        asyncTask.execute(photoModel.src?.portrait)

    }

    fun openSearchActivity(context: Context, query: String) {
        var intent = Intent(context, SearchActivity::class.java)
        intent.putExtra("query", query)
        context.startActivity(intent)
        (context as AppCompatActivity).overridePendingTransition(R.anim.act_2_in, R.anim.act_1_out)
    }

    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    fun getApiService(): ApiService {
        return ApiClient.retrofitInstance!!.create(ApiService::class.java)
    }

    fun showToast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

//    fun addToFavorite(photo: Photo, mCustomBottomSheet: LinearLayout) {
//
//        var favorite = FavoritePhoto(
//            photo.id,
//            photo.photographer,
//            photo.width,
//            photo.height,
//            photo.src?.medium!!,
//            photo.src?.portrait
//        )
//
//        var DAO = appDatabase!!.getDatabase()
//        DAO.inSertPhoto(favorite)
//        Snackbar.make(mCustomBottomSheet, "Added to favorite", Snackbar.LENGTH_SHORT).show()
//    }

}