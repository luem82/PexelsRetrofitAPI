package com.example.pexelsretrofitapi.adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.pexelsretrofitapi.R
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.example.pexelsretrofitapi.ui.MainActivity.Companion.dbHelper
import com.example.pexelsretrofitapi.ui.ViewPhotoFragment
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(
    private val list: ArrayList<Photo>,
    private var tvEmpty: TextView
) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.fav_delete.setOnClickListener {
            deleteFavorite(position, holder.itemView.context)
        }
        if (!list.isEmpty()) tvEmpty.visibility = View.GONE

    }

    private fun deleteFavorite(position: Int, context: Context) {

        var builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete this photo from favorites?")
        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }

        })
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dbHelper!!.deleteFavorite(list[position])
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                dialog?.dismiss()
                Helper.showToast(context!!, "Deleted successfully")
                if (list.isEmpty()) tvEmpty.visibility = View.VISIBLE
            }

        })
        var dialog = builder.create()
        dialog.show()

    }


    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: Photo) {

            photo.photographer.let { itemView.fav_name.text = it }
            Glide.with(itemView.context)
                .load(photo.src?.medium)
                .transform(CenterCrop())
                .into(itemView.fav_photo)

            itemView.setOnClickListener {
                val transaction =
                    (itemView.context as AppCompatActivity).getSupportFragmentManager()
                        .beginTransaction()
                val newFragment = ViewPhotoFragment.newInstance(photo, true)
                newFragment.show(transaction, "slideshow")
            }
        }
    }
}