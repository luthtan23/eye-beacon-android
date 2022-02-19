package com.luthtan.eye_beacon_android.features.testing

import android.net.Uri
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import com.luthtan.eye_beacon_android.base.BaseFragment
import com.luthtan.eye_beacon_android.databinding.FragmentTestingBinding

class TestingFragment : BaseFragment<FragmentTestingBinding, TestingViewModel>() {

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(requireContext(), "exoplayer-sample")
    }

    override val viewModel: TestingViewModel by viewModels()

    override val binding: FragmentTestingBinding by lazy {
        FragmentTestingBinding.inflate(layoutInflater)
    }

    override fun onInitViews() {
        super.onInitViews()

        initMediaVideoPlayer()
        preparePlayer("https://home01-cms-dev.webssup.com/public/upload/file/OvJ8wzbfTPdMnP602DV0f7KvqKsLEmWDquEVBh6F.mp4")

    }

//    private fun initMediaVideoPlayer() {
//        simpleExoplayer = SimpleExoPlayer.Builder(requireContext()).build()
//    }
//
//    private fun preparePlayer(url: String?) {
//        val uri = Uri.parse(url)
//        val mediaSource: ProgressiveMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//        .createMediaSource(MediaItem.fromUri(uri))
//        simpleExoplayer.setMediaSource(mediaSource)
//        simpleExoplayer.prepare()
//        binding.videoView.player = simpleExoplayer
//        simpleExoplayer.playWhenReady = true
//    }

    private fun initMediaVideoPlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = simpleExoplayer
        simpleExoplayer.playWhenReady = true
    }

    private fun preparePlayer(url: String?) {
        val uri = Uri.parse(url)

        val mediaItem = MediaItem.Builder()
            .setUri(uri)
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()

        val mediaSource: ProgressiveMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)

        simpleExoplayer.setMediaSource(mediaSource)
        simpleExoplayer.prepare()
    }
}