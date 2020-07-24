package com.example.pexelsretrofitapi.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.model.category.Theme
import com.example.pexelsretrofitapi.model.ktxs.Helper
import kotlinx.android.synthetic.main.item_theme.view.*

class ThemeAdapter : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    private val list: ArrayList<Theme>

    init {
        list = arrayListOf(
            Theme(
                "Current Events",
                "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Sea",
                "https://images.unsplash.com/photo-1471922694854-ff1b63b20054?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Travel",
                "https://images.unsplash.com/photo-1490707843862-104c1ead1918?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Nature",
                "https://images.unsplash.com/photo-1537301696988-4a82a4959466?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Wallpapers",
                "https://images.unsplash.com/photo-1593492314919-9ffa6bd21fa2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Textures & Patterns",
                "https://images.unsplash.com/photo-1593188885053-8f5dd1001b48?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "People",
                "https://images.unsplash.com/photo-1593477663875-5fffbd76dac8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Business & Work",
                "https://images.unsplash.com/photo-1495321451782-dcb9fdb512ab?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Technology",
                "https://images.unsplash.com/photo-1592500305689-d435652c10aa?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Animals",
                "https://images.unsplash.com/photo-1503855906671-63f56ad2cd7b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Interiors",
                "https://images.unsplash.com/photo-1532077678905-dbcfea8dcde1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Architecture",
                "https://images.unsplash.com/photo-1588362993295-250cf6b24ad0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Food & Drink",
                "https://images.unsplash.com/photo-1576829824786-419e0ef76b37?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Athletics",
                "https://images.unsplash.com/photo-1589893079757-7646c3644ac5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Spirituality",
                "https://images.unsplash.com/photo-1557240503-5ef8ff7a34c3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Health & Wellness",
                "https://images.unsplash.com/photo-1593204075264-0b7994458bf3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Fashion",
                "https://images.unsplash.com/photo-1513373319109-eb154073eb0b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Experimental",
                "https://images.unsplash.com/photo-1593075187910-a25371d9ef02?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "Arts & Culture",
                "https://images.unsplash.com/photo-1593071376160-9881e78495ea?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            ),
            Theme(
                "History",
                "https://images.unsplash.com/photo-1584345735668-5bce6952b2d6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
            )
        )
        list.shuffle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false)
        return ThemeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class ThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(theme: Theme) {

            theme.name?.let { itemView.theme_name.text = it }
            Glide.with(itemView.context)
                .load(theme.cover)
                .transform(CenterCrop())
                .into(itemView.theme_cover)

            itemView.setOnClickListener {
                Helper.openSearchActivity(itemView.context, theme.name)
            }
        }
    }
}