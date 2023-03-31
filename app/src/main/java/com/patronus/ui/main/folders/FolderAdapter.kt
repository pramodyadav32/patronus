package com.example.demo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.patronus.ImageClickPressed
import com.patronus.R
import com.patronus.network.model.FolderListData

class FolderAdapter(
    private val listner: ImageClickPressed
) : RecyclerView.Adapter<FolderAdapter.ComplaintHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintHolder {
        return ComplaintHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.folder_adapter, parent, false)
        )
    }

    var items: List<FolderListData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(
        holder: ComplaintHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val getData = items!![position]
        holder.textViewFolderName.text = getData.name
        holder.llFolderMain.setOnClickListener { listner.onEditClickEvent(position) }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ComplaintHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewFolderName: TextView
        var llFolderMain: LinearLayout

        init {
            textViewFolderName = itemView.findViewById(R.id.textViewFolderName)
            llFolderMain = itemView.findViewById(R.id.llFolderMain)
        }
    }
}