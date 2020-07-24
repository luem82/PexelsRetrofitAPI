package com.example.pexelsretrofitapi.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.adapter.CuratedAdapter
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.PexelsCurated
import com.example.pexelsretrofitapi.model.pexels.Photo
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_curated.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuratedFragment : Fragment() {

    private lateinit var list: ArrayList<Photo>
    private lateinit var curatedAdapter: CuratedAdapter
    private lateinit var call: Call<PexelsCurated>
    private lateinit var dialog: AlertDialog
    private val per_page = 80
    private var page = 1
    private var scroll = 80

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_curated, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = SpotsDialog(context, R.style.SpotsDialog)

        val layoutManager = GridLayoutManager(context, 2)
        rcv_curated.layoutManager = layoutManager

        loadCurated(page, per_page)

        rcv_curated.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    //load more
                    if (!dialog.isShowing) dialog.show()
                    page += 1
                    loadCurated(page, per_page)
                } else {
                    if (dialog.isShowing) dialog.dismiss()
                }
            }
        })

    }

    private fun loadCurated(idx: Int, count: Int) {
        dialog.show()
        call = Helper.getApiService().gertCurated(idx, count)
        call.enqueue(object : Callback<PexelsCurated> {
            override fun onFailure(call: Call<PexelsCurated>, t: Throwable) {
                if (dialog.isShowing) dialog.dismiss()
                Log.e("getCursted Error", t.localizedMessage)
                Helper.showToast(requireActivity(), t.message.toString())
            }

            override fun onResponse(call: Call<PexelsCurated>, response: Response<PexelsCurated>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("getCursted", response.body()!!.photos.get(0).src?.medium)
                    if (idx == 1) {
                        list = response.body()!!.photos as ArrayList<Photo>
                        curatedAdapter = CuratedAdapter(list)
                        rcv_curated.adapter = curatedAdapter
                        if (dialog.isShowing) dialog.dismiss()
                    } else {
                        if (idx <= response.body()!!.page) {
                            if (!dialog.isShowing) dialog.show()
                            var more = response.body()!!.photos
                            for (i in more.indices) {
                                list.add(more[i])
                                curatedAdapter.notifyItemInserted(list.size - 1)
                            }
                            rcv_curated.scrollToPosition(scroll)
                            scroll += 80
                            if (dialog.isShowing) dialog.dismiss()
                        } else {
                            Toast.makeText(context, "End of lists.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    if (dialog.isShowing) dialog.dismiss()
                    Log.e("Null", "Null Response")
                    Helper.showToast(requireActivity(), "Null Response")
                }
            }

        })
    }
}