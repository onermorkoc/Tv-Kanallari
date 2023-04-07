package com.onermorkoc.tvkanallari.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onermorkoc.tvkanallari.R
import com.onermorkoc.tvkanallari.adapter.ChannelCategoryAdapter.*
import com.onermorkoc.tvkanallari.ui.HomeScreen
import kotlinx.android.synthetic.main.rview_channel_category.view.*

class ChannelCategoryAdapter(): RecyclerView.Adapter<ChannelCategoryAdapterVH>() {

    class ChannelCategoryAdapterVH(itemview: View): RecyclerView.ViewHolder(itemview) {}
    private lateinit var context: Context
    private lateinit var onListItemClick: HomeScreen.OnListItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelCategoryAdapterVH {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.rview_channel_category, parent, false)
        return ChannelCategoryAdapterVH(layoutInflater)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ChannelCategoryAdapterVH, position: Int) {

        var string = ""
        var img =0

        when(position) {
            0->{
                img = R.drawable.all
                string = context.getString(R.string.all)
            }
            1->{
                img = R.drawable.news
                string = context.getString(R.string.news)
            }
            2->{
                img = R.drawable.sport
                string = context.getString(R.string.sport)
            }
            3->{
                img = R.drawable.music
                string = context.getString(R.string.music)
            }
            4->{
                img = R.drawable.child
                string = context.getString(R.string.child)
            }
            5->{
                img = R.drawable.documentary
                string = context.getString(R.string.documentary)
            }
        }
        holder.itemView.homeScreen_category_img_id.setImageResource(img)
        holder.itemView.homeScreen_category_name_id.setText(string)

        holder.itemView.homeScreen_category_selected_id.setOnClickListener {
            onListItemClick.onClick(string)
        }
    }

    fun setOnListItemClick(context: HomeScreen.OnListItemClick){
        onListItemClick = context
    }
}