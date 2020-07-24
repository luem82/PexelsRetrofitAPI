package com.example.pexelsretrofitapi.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.pexelsretrofitapi.adapter.CuratedAdapter
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.PexelsCurated
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.example.pexelsretrofitapi.room.AppDatabase
import com.example.pexelsretrofitapi.room.FavoritePhoto
import com.example.sqlitedatabase.DBHelper
import kotlinx.android.synthetic.main.fragment_curated.*
import me.ibrahimsn.lib.SmoothBottomBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R


class MainActivity : AppCompatActivity() {

//    companion object {
//        var appDatabase: AppDatabase? = null
//    }

    companion object {
        var dbHelper: DBHelper? = null
    }

    lateinit var smoothBottomBar: SmoothBottomBar
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(com.example.pexelsretrofitapi.R.layout.activity_main)

//        appDatabase = Room.databaseBuilder(
//            this,
//            AppDatabase::class.java, "photo_database"
//        ).allowMainThreadQueries().build()

        dbHelper = DBHelper(this)

        smoothBottomBar = findViewById(com.example.pexelsretrofitapi.R.id.bottom_bar)
        navController = findNavController(com.example.pexelsretrofitapi.R.id.nav_host_fragment)

    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.pexelsretrofitapi.R.menu.bottom_nav_menu, menu)
        smoothBottomBar.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
}
