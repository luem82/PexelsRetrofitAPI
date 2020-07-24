package com.example.pexelsretrofitapi.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.adapter.PhotoAdapter
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.PexelsSearch
import com.example.pexelsretrofitapi.model.pexels.Photo
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_curated.*
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var list: ArrayList<Photo>
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var call: Call<PexelsSearch>
    private lateinit var dialog: AlertDialog
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private var page = 1
    private var per_page = 80
    private var searchKey = ""
    private var scroll = 80

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = arrayListOf()
        dialog = SpotsDialog(context, R.style.SpotsDialog)
        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rcv_search.layoutManager = layoutManager
        photoAdapter = PhotoAdapter(list)
        rcv_search.adapter = photoAdapter

        rcv_search.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    //load more
                    page += 1
                    getPhotos(searchKey, page, per_page)
                } else {
                    if (dialog.isShowing) dialog.dismiss()
                }
            }

        })

        search_bar.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        scroll = 80
        page = 1
        searchKey = query?.trim().toString()
        text_result.visibility = View.GONE
        if (list.size > 0) {
            list.clear()
            photoAdapter.notifyDataSetChanged()
        }

        getPhotos(searchKey, page, per_page)
        Helper.hideKeyboard(requireActivity())

        return true
    }

    private fun getPhotos(key: String, idx: Int, count: Int) {
        dialog.show()
        call = Helper.getApiService().searchPexelsPhotos(key, idx, count)
        call.enqueue(object : Callback<PexelsSearch> {
            override fun onFailure(call: Call<PexelsSearch>, t: Throwable) {
                Log.e("serach error", t.localizedMessage)
                Helper.showToast(requireActivity(), t.localizedMessage)
                if (dialog.isShowing) dialog.dismiss()
                text_result.visibility = View.VISIBLE
                text_result.text = "Error: ${t.localizedMessage}"
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
                            rcv_search.scrollToPosition(scroll)
                            scroll += 80
                            if (dialog.isShowing) dialog.dismiss()
                        } else {
                            Helper.showToast(requireActivity(), "End of list")
                        }
                    }

                } else {
                    Log.e("serach null", "Null data")
                    Helper.showToast(activity!!, "Null data")
                    if (dialog.isShowing) dialog.dismiss()
                    text_result.visibility = View.VISIBLE
                    text_result.text = "Error"
                }
            }

        })
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}