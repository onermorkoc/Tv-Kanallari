package com.onermorkoc.tvkanallari.adapter

import android.annotation.SuppressLint
import android.content.Context
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
    private lateinit var context: Context
    private var currentItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayTvScreenRviewVH {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rview_channel_list_basic, parent, false)
        return PlayTvScreenRviewVH(inflater)
    }

    override fun getItemCount(): Int {
        return channelArrayList.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PlayTvScreenRviewVH, @SuppressLint("RecyclerView") position: Int) {

        val id = channelArrayList[position].id
        val name = channelArrayList[position].name

        holder.itemView.playTvScreen_ch_name_id.setText((id + 1).toString() + "-" + name)

        holder.itemView.playTvScreen_selected_ch_id.setOnClickListener {
            currentItem = position
            notifyDataSetChanged()
            onListItemClick.onClick(id)
        }

        if (currentItem == position){
            holder.itemView.setBackgroundColor(context.getColor(R.color.currentItem))
        }else{
            holder.itemView.setBackgroundColor(context.getColor(R.color.black))
        }
    }

    fun setOnListItemClick(context: PlayTvScreen.OnListItemClick){
        onListItemClick = context
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentItem(currentItem: Int){
        this.currentItem = currentItem
        notifyDataSetChanged()
    }
}