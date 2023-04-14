package com.onermorkoc.tvkanallari.ui

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaPlayer.OnInfoListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onermorkoc.tvkanallari.R
import com.onermorkoc.tvkanallari.adapter.PlayTvScreenRviewAdapter
import com.onermorkoc.tvkanallari.data.ChannelData
import kotlinx.android.synthetic.main.play_tv_screen.*

@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class PlayTvScreen : Fragment() {

    private var id: Int = 0
    private lateinit var channelArrayList: ArrayList<ChannelData>
    private lateinit var hideUiRunnable: Runnable
    private lateinit var showUiRunnable: Runnable
    private lateinit var onListItemClick: OnListItemClick
    private lateinit var adapter: PlayTvScreenRviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreenActivity(1)

        arguments.let {
            id = PlayTvScreenArgs.fromBundle(it!!).id
        }

        channelArrayList = HomeScreen.getChannelArrayList.channelArrayList

        hideUiRunnable = Runnable {
            playTvScreen_rview_id.visibility = View.GONE
            customMediaController_id.visibility = View.GONE
        }

        showUiRunnable = Runnable {
            playTvScreen_rview_id.visibility = View.VISIBLE
            customMediaController_id.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.play_tv_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startVideo(channelArrayList[id].url)
        setupCustomMediaController()
        setupOnListItemClick()
        setupRecyclerView()
        backButton()
        setupProgressBar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupCustomMediaController() {

        playTvScreen_videoView_id.setOnClickListener {// media controlleri gösterme gizleme olayı
            if (playTvScreen_rview_id.visibility == View.GONE)
                showUiRunnable.run()
            else
                hideUiRunnable.run()
        }

        playTvScreen_next_id.setOnClickListener { // sonraki kanal butonu
            if (++id == channelArrayList.size)
                id = 0
            setRviewSelectedChannel(id)
            startVideo(channelArrayList[id].url)
        }

        playTvScreen_back_id.setOnClickListener { // önceki kanal butonu
            if (--id == -1)
                id = channelArrayList.size - 1
            setRviewSelectedChannel(id)
            startVideo(channelArrayList[id].url)
        }

        playTvScreen_playPause_id.setOnClickListener { // durdur-başlat tuşu
            if (playTvScreen_videoView_id.isPlaying){
                playTvScreen_videoView_id.pause()
                playTvScreen_playPause_img_id.setImageResource(R.drawable.play)
            }else{
                startVideo(channelArrayList[id].url)
                playTvScreen_playPause_img_id.setImageResource(R.drawable.pause)
            }
        }
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext())
        playTvScreen_rview_id.layoutManager = layoutManager
        adapter = PlayTvScreenRviewAdapter(channelArrayList)
        playTvScreen_rview_id.adapter = adapter
        setRviewSelectedChannel(id)
        adapter.setOnListItemClick(onListItemClick)
    }

    private fun startVideo(url: String){
        progressBar_id.visibility = View.VISIBLE
        playTvScreen_videoView_id.setVideoPath(url)
        playTvScreen_videoView_id.start()
    }

    private fun backButton(){
        requireActivity().onBackPressedDispatcher.addCallback{
            if (playTvScreen_rview_id.visibility == View.VISIBLE){
                hideUiRunnable.run()
            }else{
                fullScreenActivity(0)
                val action = PlayTvScreenDirections.actionPlayTvScreenToHomeScreen()
                findNavController().navigate(action)
            }
        }
    }

    interface OnListItemClick {
        fun onClick(id: Int)
    }

    private fun setupOnListItemClick(){
        onListItemClick = object : OnListItemClick{
           override fun onClick(id: Int) {
               this@PlayTvScreen.id = id
               startVideo(channelArrayList[id].url)
           }
       }
    }

    private fun setupProgressBar(){ // video oynuyosa progressbarı gizle
        playTvScreen_videoView_id.setOnInfoListener(object: OnInfoListener {
            override fun onInfo(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
                if (p0 != null)
                    if (p0.isPlaying)
                        progressBar_id.visibility = View.GONE
                return true
            }
        })
    }

    private fun setRviewSelectedChannel(index: Int){
        adapter.setCurrentItem(index)
        playTvScreen_rview_id.smoothScrollToPosition(index)
    }

    private fun fullScreenActivity(mode: Int){ // source = https://stackoverflow.com/a/66526368

        val windowInsetsController = WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        if (mode == 1)
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        else
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }
}