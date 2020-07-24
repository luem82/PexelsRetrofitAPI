package com.example.pexelsretrofitapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.adapter.FavoriteAdapter
import com.example.pexelsretrofitapi.ui.MainActivity.Companion.dbHelper
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = dbHelper!!.getFavorites()
        var adapter = FavoriteAdapter(list,tv_empty)
        var layoutManager = GridLayoutManager(context, 2)
        rcv_favorite.layoutManager = layoutManager
        rcv_favorite.setHasFixedSize(true)
        rcv_favorite.itemAnimator = DefaultItemAnimator()
        rcv_favorite.adapter = adapter


    }

}