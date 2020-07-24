package com.example.pexelsretrofitapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.adapter.PhotoAdapter.Companion.VIEW_TYPE_DATA
import com.example.pexelsretrofitapi.adapter.PhotoAdapter.Companion.VIEW_TYPE_PROGRESS
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.example.pexelsretrofitapi.ui.ViewPhotoFragment
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(private var list: ArrayList<Photo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_DATA = 0;
        const val VIEW_TYPE_PROGRESS = 1;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> {
                var view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo, parent, false)
                PhotoViewHolder(view)
            }
            VIEW_TYPE_PROGRESS -> {
                var view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_load_progress, parent, false)
                ProgressViewHolder(view)
            }
            else -> throw IllegalArgumentException("Different View type")
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoViewHolder) {
            (holder as PhotoViewHolder).bind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        var viewType = list[position].photographer
        return when (viewType) {
            "loadmorephoto" -> VIEW_TYPE_PROGRESS
            else -> VIEW_TYPE_DATA
        }
    }

    inner class PhotoViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(photo: Photo) {

            var requestOptions = RequestOptions().placeholder(android.R.drawable.stat_sys_download)
            Glide.with(itemView.context)
                .load(photo.src?.medium)
                .apply(requestOptions)
                .into(itemView.iv_photo)

            itemView.setOnClickListener {
                val transaction =
                    (itemView.context as AppCompatActivity).getSupportFragmentManager()
                        .beginTransaction()
                val newFragment = ViewPhotoFragment.newInstance(photo, false)
                newFragment.show(transaction, "slideshow")
            }
        }
    }

    inner class ProgressViewHolder(item: View) : RecyclerView.ViewHolder(item)

}