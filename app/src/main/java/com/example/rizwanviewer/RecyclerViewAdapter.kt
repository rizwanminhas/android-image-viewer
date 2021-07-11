package com.example.rizwanviewer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File

class RecyclerViewAdapter constructor(context: Context, imagePathArrayList: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    private var context = context
    private var imagePathArrayList = imagePathArrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val imgFile = File(imagePathArrayList!![position])
        if (imgFile.exists()) {

            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageIV)

            holder.itemView.setOnClickListener { // inside on click listener we are creating a new intent
                val i = Intent(context, ImageDetailActivity::class.java)

                i.putExtra("imgPath", imagePathArrayList!![position])

                context!!.startActivity(i)
            }
        }
    }

    override fun getItemCount(): Int {
        return imagePathArrayList!!.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIV: ImageView

        init {
            imageIV = itemView.findViewById(R.id.idIVImage)
        }
    }
}