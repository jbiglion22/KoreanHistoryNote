package com.jbiglion22.koreanhistorynote

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class TitleNameAdapter(val titlenameList: ArrayList<TitleName>) : RecyclerView.Adapter<TitleNameAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleNameAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_titlename, parent, false)
        val btnImage = view.findViewById<ImageButton>(R.id.imageButton)
        btnImage.setOnClickListener {
           Toast.makeText(parent.context, "test", Toast.LENGTH_LONG).show()
        }

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val titlename: TitleName = titlenameList.get(curPos)

                val intent = Intent(parent.context, ContentActivity::class.java)
                intent.putExtra("curPos", curPos)
                parent.context.startActivity(intent)


            }
        }
    }

    override fun getItemCount(): Int {
        return titlenameList.size
    }

    override fun onBindViewHolder(holder: TitleNameAdapter.CustomViewHolder, position: Int) {
        holder.name.text = titlenameList.get(position).name
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name=itemView.findViewById<TextView>(R.id.tv_name)
    }
}