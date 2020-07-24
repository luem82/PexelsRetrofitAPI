package com.example.pexelsretrofitapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.example.pexelsretrofitapi.ui.ViewPhotoFragment
import kotlinx.android.synthetic.main.item_curated.view.*
import kotlinx.android.synthetic.main.item_theme.view.*
import java.io.Serializable

class CuratedAdapter(private val list: ArrayList<Photo>) :
    RecyclerView.Adapter<CuratedAdapter.CuratedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_curated, parent, false)
        return CuratedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CuratedViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class CuratedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: Photo) {

            photo.photographer.let { itemView.curated_name.text = it }
            Glide.with(itemView.context)
                .load(photo.src?.medium)
                .transform(CenterCrop())
                .into(itemView.curated_photo)

            itemView.setOnClickListener {
                val transaction =
                    (itemView.context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                val newFragment = ViewPhotoFragment.newInstance(photo ,false)
                newFragment.show(transaction, "slideshow")
            }
        }
    }
}