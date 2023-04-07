package com.onermorkoc.tvkanallari.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onermorkoc.tvkanallari.R
import com.onermorkoc.tvkanallari.adapter.HomeScreenRviewAdapter.*
import com.onermorkoc.tvkanallari.data.ChannelData
import com.onermorkoc.tvkanallari.ui.HomeScreenDirections
import kotlinx.android.synthetic.main.rview_channel_list.view.*

class HomeScreenRviewAdapter(val channelArrayList: ArrayList<ChannelData>): RecyclerView.Adapter<HomeScreenRviewVH>() {

    class HomeScreenRviewVH(itemview: View): RecyclerView.ViewHolder(itemview) {}
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenRviewVH {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rview_channel_list, parent, false)
        return HomeScreenRviewVH(inflater)
    }

    override fun getItemCount(): Int {
        return channelArrayList.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: HomeScreenRviewVH, position: Int) {

        val id = channelArrayList[position].id
        val name = channelArrayList[position].name
        val photo = channelArrayList[position].photo

        Glide.with(context).load(photo).placeholder(R.drawable.loading).into(holder.itemView.homeScreen_ch_img_id)
        holder.itemView.homeScreen_ch_name_id.setText(name)

        holder.itemView.homeScreen_selected_ch_id.setOnClickListener {
            val action = HomeScreenDirections.actionHomeScreenToPlayTvScreen(id)
            Navigation.findNavController(it).navigate(action)
        }
    }
}