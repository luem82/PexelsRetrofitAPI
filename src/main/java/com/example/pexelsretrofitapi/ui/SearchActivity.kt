package com.example.pexelsretrofitapi.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.adapter.PhotoAdapter
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.PexelsSearch
import com.example.pexelsretrofitapi.model.pexels.Photo
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<Photo>
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var call: Call<PexelsSearch>
    private lateinit var dialog: AlertDialog
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private var page = 1
    private var per_page = 80
    private var query = ""
    private var scroll = 80


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_search)

        query = intent.getStringExtra("query")
        tv_catrgory.text = query
        iv_back.setOnClickListener {
            onBackPressed()
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        list = arrayListOf()
        dialog = SpotsDialog(this, R.style.SpotsDialog)
        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rcv_search_2.layoutManager = layoutManager
        photoAdapter = PhotoAdapter(list)
        rcv_search_2.adapter = photoAdapter
        getPhotos(query, page, per_page)

        rcv_search_2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    //load more
                    page += 1
                    getPhotos(query, page, per_page)
                } else {
                    if (dialog.isShowing) dialog.dismiss()
                }
            }

        })
    }

    private fun getPhotos(key: String, idx: Int, count: Int) {
        dialog.show()
        call = Helper.getApiService().searchPexelsPhotos(key, idx, count)

        call.enqueue(object : Callback<PexelsSearch> {
            override fun onFailure(call: Call<PexelsSearch>, t: Throwable) {
                Log.e("serach error", t.localizedMessage)
                Helper.showToast(this@SearchActivity, t.localizedMessage)
                if (dialog.isShowing) dialog.dismiss()
            }

            override fun onResponse(call: Call<PexelsSearch>, response: Response<PexelsSearch>) {
                if (response.isSuccessful && response.body() != null) {

                    if (idx == 1) {
                        list.addAll(response.body()!!.photos)
                        photoAdapter.notifyDataSetChanged()
                        if (dialog.isShowing) dialog.dismiss()
                    } else {
                        if (list.size <= response.body()!!.totalResults) {
                            var more = response.body()!!.photos
                            for (i in more.indices) {
                                list.add(more[i])
                                photoAdapter.notifyItemInserted(list.size - 1)
                            }
                            rcv_search_2.scrollToPosition(scroll)
                            scroll += 80
                            if (dialog.isShowing) dialog.dismiss()
                        } else {
                            Helper.showToast(this@SearchActivity, "End of list")
                        }
                    }

                } else {
                    Log.e("serach null", "Null data")
                    Helper.showToast(this@SearchActivity, "Null data")
                    if (dialog.isShowing) dialog.dismiss()
                }
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.act_1_in, R.anim.act_2_out)
    }
}
