package com.example.pexelsretrofitapi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.adapter.ColorAdapter
import com.example.pexelsretrofitapi.adapter.ThemeAdapter
import com.example.pexelsretrofitapi.model.category.Theme
import com.example.pexelsretrofitapi.model.ktxs.ThemeItemDecoration
import com.example.pexelsretrofitapi.model.ktxs.Consts
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.ktxs.dpToPix
import com.example.pexelsretrofitapi.model.pexels.PexelsSearch
import com.example.pexelsretrofitapi.network.ApiClient
import com.example.pexelsretrofitapi.network.ApiService
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var themeAdapter: ThemeAdapter
    private lateinit var colorAdapter: ColorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcv_theme.layoutManager = GridLayoutManager(context, 2)
        themeAdapter = ThemeAdapter()
        rcv_theme.adapter = themeAdapter

        rcv_color.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        colorAdapter = ColorAdapter()
        rcv_color.adapter = colorAdapter
    }

}