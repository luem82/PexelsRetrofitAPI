package com.example.pexelsretrofitapi.adapter

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.model.category.Color
import com.example.pexelsretrofitapi.model.ktxs.Helper
import kotlinx.android.synthetic.main.item_color.view.*
import kotlinx.android.synthetic.main.item_theme.view.*

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ThemeViewHolder>() {

    private val list: ArrayList<Color>

    init {
        list = arrayListOf(
            Color(R.color.colorRed, "Red"),
            Color(R.color.colorBlack, "Black"),
            Color(R.color.colorGreen, "Green"),
            Color(R.color.colorBlue, "Blue"),
            Color(R.color.colorLight, "Light"),
            Color(R.color.colorViolet, "Violet"),
            Color(R.color.colorYellow, "Yellow"),
            Color(R.color.colorPink, "Pink"),
            Color(R.color.colorOrange, "Orange"),
            Color(R.color.colorPurple, "Purple")
        )
        list.shuffle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ThemeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class ThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(color: Color) {

            color.name?.let { itemView.color_name.text = it }

            color.rsc?.let {
                itemView.color_rsc.setImageTintList(
                    itemView.context.resources.getColorStateList(it)
                )
            }

            itemView.setOnClickListener {
                Helper.openSearchActivity(itemView.context, color.name)
            }
        }
    }
}