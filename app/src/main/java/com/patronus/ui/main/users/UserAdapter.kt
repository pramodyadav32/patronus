package com.example.demo

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patronus.ImageClickPressed
import com.patronus.R
import com.patronus.network.model.UserListData

class UserAdapter(
    private val listner: ImageClickPressed
) : RecyclerView.Adapter<UserAdapter.ComplaintHolder>() {

    private var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintHolder {
        mContext = parent.context
        return ComplaintHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_adapter, parent, false)
        )
    }

    var items: List<UserListData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(
        holder: ComplaintHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val getData = items!![position]
        holder.textViewName.text = getData.firstName + " " + getData.lastName
        holder.gender.text = getData.gender
        holder.phoneNo.text = getData.phoneNumber
        var stickers = "";
        for(temp in getData.stickers){
            stickers = stickers + " " + temp
        }
        holder.stickers.text = stickers

        try {
            if(getData.imageUrl != null) {
                holder.profileNameText.visibility = View.GONE
                holder.holder_img.visibility = View.VISIBLE
                val options: com.bumptech.glide.request.RequestOptions =
                    com.bumptech.glide.request.RequestOptions()
                        .centerCrop()

                Glide.with(mContext!!).load(getData.imageUrl).apply(options).into(holder.holder_img)
            }else{
                holder.holder_img.visibility = View.GONE
                holder.profileNameText.visibility = View.VISIBLE
                val shortName = getData.firstName.first().toString() + getData.lastName.first().toString()
                holder.profileNameText.text = shortName
            }
        } catch (e: Exception) {
            Log.d("PATH", e.toString())
        }
        holder.llFolderMain.setOnClickListener { listner.onEditClickEvent(position) }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ComplaintHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var holder_img: ImageView
        var textViewName: TextView
        var profileNameText: TextView
        var gender: TextView
        var phoneNo: TextView
        var stickers: TextView
        var llFolderMain: LinearLayout

        init {
            textViewName = itemView.findViewById(R.id.name)
            profileNameText = itemView.findViewById(R.id.profileNameText)
            gender = itemView.findViewById(R.id.gender)
            phoneNo = itemView.findViewById(R.id.phoneNo)
            stickers = itemView.findViewById(R.id.stickers)
            holder_img = itemView.findViewById(R.id.holder_img)
            llFolderMain = itemView.findViewById(R.id.root_layout)
        }
    }
}