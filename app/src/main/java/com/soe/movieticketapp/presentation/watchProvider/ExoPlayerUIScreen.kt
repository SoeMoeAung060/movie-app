package com.soe.movieticketapp.presentation.watchProvider

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

@Composable
fun ExoPlayerUIScreen(
    modifier: Modifier = Modifier,
    uri: String) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        ExoPlayerSetUp(
            uri = "https://www.youtube.com/watch?v=MCsG9zH7DJE"
        )
    }

}


@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerSetUp(
    modifier: Modifier = Modifier,
    uri : String,
){

    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val context = LocalContext.current

    val mediaItem = MediaItem.fromUri(uri)

    val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
        .createMediaSource(mediaItem)


    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            try {
                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true
            } catch (e: ExoPlaybackException) {
                Log.e("ExoPlayer", "Playback error: ${e.message}")
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = lifecycleOwner) {
            val observer = LifecycleEventObserver{ _, event ->
                lifecycle = event
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                exoPlayer.release()
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }


    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        factory = {
            PlayerView(context).apply {
                this.player = exoPlayer
            }
        }
    )


}

