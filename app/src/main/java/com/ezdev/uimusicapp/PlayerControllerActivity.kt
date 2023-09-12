package com.ezdev.uimusicapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezdev.uimusicapp.ui.theme.UiMusicAppTheme

class PlayerControllerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  get data (song want to play) from main activity
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            song = bundle.get("bundle_song") as Song
        }

        setContent {
            UiMusicAppTheme {
                PlayerControllerScreen()
            }
        }
    }
}

var song: Song =
    Song("Demons", "Imagine Dragons", R.drawable.banner_demons_song, 3 * 60 + 57, "lyric")

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerControllerScreen() {

    UiMusicAppTheme(
        darkTheme = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            TopBar()
            CardSong(song = song)
            ProgressController(song = song)
            ButtonController()
            TextLyric(song = song)
        }
    }
}


@Composable
fun TopBar(modifier: Modifier = Modifier) {
    //  data
    val context = LocalContext.current as Activity
    //  ui
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                context.finish()
            },
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "back button",
                modifier = Modifier.size(16.dp)
            )
        }
        Text(text = "Playing now", style = MaterialTheme.typography.titleLarge)
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "list button",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

@Composable
fun CardSong(modifier: Modifier = Modifier, song: Song) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = 32.dp)
                .shadow(16.dp, CircleShape)
                .clip(CircleShape)
                .fillMaxWidth(0.75f),

            painter = painterResource(id = song.banner), contentDescription = song.title,
            contentScale = ContentScale.Crop
        )
        Text(text = song.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = song.singer, modifier = Modifier.alpha(0.5f))
    }
}

fun secondToMinute(secondTime: Int): String {
    val minute = secondTime / 60
    val secondTimeRemaining = if (secondTime % 60 == 0) 0 else secondTime % 60
    return "$minute:$secondTimeRemaining"
}

@Composable
fun ProgressController(modifier: Modifier = Modifier, song: Song) {
    //  data
    var progress by remember { mutableStateOf(0f) }
    val goneDuration = (progress * song.length).toInt()
    val remainingDuration = song.length - goneDuration
    println("length = " + song.length)
    println("progress = " + progress)
    println("gone = $progress x ${song.length} = $goneDuration")

    //  ui
    Column(modifier = modifier.fillMaxWidth()) {
        Slider(value = progress, onValueChange = {
            progress = it
        })
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = secondToMinute(goneDuration))
            Text(text = "-" + secondToMinute(remainingDuration))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ButtonController(modifier: Modifier = Modifier) {
    //  data
    var isPlaying by remember { mutableStateOf(true) }

    //  ui
    Row(
        modifier = modifier
            .fillMaxWidth(),
        //.padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.alpha(0.5f),
            onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Autorenew, contentDescription = "play loop button")
        }
        IconButton(
            modifier = Modifier,
            onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = "previous song button"
            )
        }
        IconButton(
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 16.dp, horizontal = 12.dp),
            onClick = { isPlaying = !isPlaying }) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "play song button",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        IconButton(
            modifier = Modifier,
            onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.SkipNext, contentDescription = "next song button")
        }
        IconButton(modifier = Modifier.alpha(0.5f),
            onClick = { /*TODO*/ }) {
            Icon(

                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "volume button"
            )
        }
    }
}


@Composable
fun TextLyric(modifier: Modifier = Modifier, song: Song) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = song.lyric)
    }
}

