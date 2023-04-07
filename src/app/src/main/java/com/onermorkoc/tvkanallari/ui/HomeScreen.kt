package com.onermorkoc.tvkanallari.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.onermorkoc.tvkanallari.R
import com.onermorkoc.tvkanallari.adapter.ChannelCategoryAdapter
import com.onermorkoc.tvkanallari.adapter.HomeScreenRviewAdapter
import com.onermorkoc.tvkanallari.data.ChannelData
import com.onermorkoc.tvkanallari.data.ChannelDataAPI
import kotlinx.android.synthetic.main.home_screen.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreen : Fragment() {

    private var channelArrayList = arrayListOf<ChannelData>()
    private var filterChannelList = arrayListOf<ChannelData>()
    private var job: Job? = null
    private lateinit var onListItemClick: OnListItemClick

    companion object getChannelArrayList{ // playTvScreen de channelArrayList almak için yaptım
        var channelArrayList = arrayListOf<ChannelData>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadData()
        setupSearchView()
        backButton()
        setupOnListItemClick()
        setupChannelCategoryRview()
    }

    private fun downloadData(){

        val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ChannelDataAPI::class.java)

        job = CoroutineScope(Dispatchers.Default).launch {
            val response = retrofit.getData()
            if (response.isSuccessful){
                response.body()?.let { list->
                    channelArrayList = list
                    totalChannel()
                    getChannelArrayList.channelArrayList = channelArrayList
                    withContext(Dispatchers.Main){
                        setupRecyclerView()
                    }
                }
            }
        }
    }

    private fun totalChannel(){
        homeScreen_searchView_id.queryHint = requireActivity().getString(R.string.total_channel) + " ${channelArrayList.size}"
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        homeScreen_rview_id.layoutManager = layoutManager
        val adapter = HomeScreenRviewAdapter(channelArrayList)
        homeScreen_rview_id.adapter = adapter
    }

    private fun setupSearchView(){
        homeScreen_searchView_id.setOnQueryTextListener(object: OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    filterChannelList.clear()
                    channelArrayList.forEach {
                        if(it.name.lowercase().contains(newText)){
                            filterChannelList.add(it)
                        }
                    }
                    homeScreen_rview_id.adapter = HomeScreenRviewAdapter(filterChannelList)
                }else{
                    homeScreen_rview_id.adapter = HomeScreenRviewAdapter(channelArrayList)
                }
                return true
            }
        })
    }

    private fun backButton(){
        requireActivity().onBackPressedDispatcher.addCallback{
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun setupChannelCategoryRview(){
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        homeScreen_category_rview_id.layoutManager = layoutManager
        val adapter = ChannelCategoryAdapter()
        homeScreen_category_rview_id.adapter = adapter
        adapter.setOnListItemClick(onListItemClick)
    }

    interface OnListItemClick{
        fun onClick(category: String)
    }

    private fun setupOnListItemClick(){
        onListItemClick = object : OnListItemClick{
            override fun onClick(category: String) {
                if (category == requireActivity().getString(R.string.all)) {
                    homeScreen_rview_id.adapter = HomeScreenRviewAdapter(channelArrayList)
                } else{
                    filterChannelList.clear()
                    channelArrayList.forEach {
                        if(it.category.contains(category)){
                            filterChannelList.add(it)
                        }
                    }
                    homeScreen_rview_id.adapter = HomeScreenRviewAdapter(filterChannelList)
                }
            }
        }
    }
}