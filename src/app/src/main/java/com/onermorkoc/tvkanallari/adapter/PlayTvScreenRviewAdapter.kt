package com.onermorkoc.tvkanallari.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onermorkoc.tvkanallari.R
import androidx.recyclerview.widget.RecyclerView
import com.onermorkoc.tvkanallari.adapter.PlayTvScreenRviewAdapter.PlayTvScreenRviewVH
import com.onermorkoc.tvkanallari.data.ChannelData
import com.onermorkoc.tvkanallari.ui.PlayTvScreen
import kotlinx.android.synthetic.main.rview_channel_list_basic.view.*

class PlayTvScreenRviewAdapter(val channelArrayList: ArrayList<ChannelData>): RecyclerView.Adapter<PlayTvScreenRviewVH>() {

    class PlayTvScreenRviewVH(itemview: View): RecyclerView.ViewHolder(itemview) {}
    private lateinit var onListItemClick: PlayTvScreen.OnListItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayTvScreenRviewVH {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rview_channel_list_basic, parent, false)
        return PlayTvScreenRviewVH(inflater)
    }

    override fun getItemCount(): Int {
        return channelArrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlayTvScreenRviewVH, position: Int) {

        val id = channelArrayList[position].id
        val name = channelArrayList[position].name

        holder.itemView.playTvScreen_ch_name_id.setText((id + 1).toString() + "-" + name)

        holder.itemView.playTvScreen_selected_ch_id.setOnClickListener {
            onListItemClick.onClick(id)
        }
    }

    fun setOnListItemClick(context: PlayTvScreen.OnListItemClick){
        onListItemClick = context
    }
}