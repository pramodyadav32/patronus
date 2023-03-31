package com.patronus.ui.main.files

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patronus.ImageClickPressed
import com.patronus.R
import com.patronus.network.model.FileListData
import com.patronus.network.model.FolderListData

class FileAdapter(
    private val listner: ImageClickPressed
) : RecyclerView.Adapter<FileAdapter.ComplaintHolder>() {
    private var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintHolder {
        mContext = parent.context
        return ComplaintHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.file_adapter, parent, false)
        )
    }

    var items: List<FileListData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(
        holder: ComplaintHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val getData = items!![position]
        holder.textViewImageName.text = getData.fileName

        if (getData.contentType.equals("image/jpeg")) {
            holder.image.setVisibility(View.VISIBLE)
            holder.view2.setVisibility(View.GONE)
            try {
                val options: com.bumptech.glide.request.RequestOptions = com.bumptech.glide.request.RequestOptions()
                    .centerCrop()

                Glide.with(mContext!!).load(getData.previewUrl).apply(options).into(holder.image)
                Log.d("PATH", getData.previewUrl)
            } catch (e: Exception) {
                Log.d("PATH", e.toString())
            }
        } else {
            holder.view2.setVisibility(View.VISIBLE)
            holder.image.setVisibility(View.GONE)
        }

        holder.llFileMain.setOnClickListener {
            listner.onEditClickEvent(position);
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ComplaintHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewImageName: TextView
        var image: ImageView
        var view2: CardView
        var llFileMain: LinearLayout

        init {
            textViewImageName = itemView.findViewById(R.id.textViewImageName)
            image = itemView.findViewById(R.id.image)
            view2 = itemView.findViewById(R.id.view2)
            llFileMain = itemView.findViewById(R.id.llFileMain)
        }
    }
}