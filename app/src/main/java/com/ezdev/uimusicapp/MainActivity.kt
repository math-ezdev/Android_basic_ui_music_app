package com.ezdev.uimusicapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezdev.uimusicapp.ui.theme.UiMusicAppTheme
import java.io.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  hide status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!.hide(
                android.view.WindowInsets.Type.statusBars()
            )
        } // else{ themes.xml <style.FullScreen/> }
        setContent {
            UiMusicAppTheme {
                HomeScreen()
            }
        }
    }
}

/*  #design ui for music app
        _screen: home
            _playing song
            _playlist: horizontal list
            _vertical list
            _bottom bar
        _screen: player controller
            _top bar
            _card song
            _progress
            _controller button
            _text lyric
* */

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    //  data
    val context = LocalContext.current
    val navigate = { song: Song ->
        println(song)
        val intent = Intent(context, PlayerControllerActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("bundle_song", song)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

    //  ui
    UiMusicAppTheme(
        darkTheme = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ListSong(modifier = Modifier.weight(1f), onClick = navigate)
            BottomBar()
        }
    }
}

@Composable
fun CardPlayingSong(
    modifier: Modifier = Modifier,
    song: Song,
    onClick: (Song) -> Unit
) {
    //  data
    var isPlaying by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }


    //  ui
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .height(IntrinsicSize.Min)
            .clickable {
                onClick(song)
            },
        shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(24.dp, 80.dp, 24.dp, 32.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = song.banner),
                contentDescription = "image song",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .shadow(4.dp, CircleShape)
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),

                )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                )
                Text(
                    text = song.singer,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    modifier = Modifier.alpha(0.7f),
                    overflow = TextOverflow.Ellipsis

                )
            }

            IconButton(onClick = {
                isFavorite = !isFavorite
            }) {
                Icon(
                    painterResource(id = R.drawable.icon_favorite),
                    contentDescription = "favorite button",
                    tint = if (isFavorite) Color.Unspecified else MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(onClick = {
                isPlaying = !isPlaying
            }

            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Default.PlayArrow,
                    contentDescription = "play or pause button",
                    modifier = Modifier
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                        .padding(8.dp)

                )
            }
        }
    }
}

data class Song(
    val title: String,
    val singer: String,
    val banner: Int,
    val length: Int,
    val lyric: String
) : Serializable


val demons = Song("Demons", "Imagine Dragons", R.drawable.banner_demons_song, 3*60+57, "lyric")
val sevenYears = Song("7 years", "Lukas Graham", R.drawable.banner_7years_song, 4*60, "lyric")
val cheapThrills =
    Song("Cheap Thrills", "Sia", R.drawable.banner_cheap_thrills_song, 4*60+22, "lyric")
val cykaBlyat =
    Song(
        "Cyka Blyat",
        "DJ Blyatman & Russian Village Boys",
        R.drawable.banner_cyka_blyat_song,
        3*60+3,
        "lyric"
    )

val listSong = listOf(demons, sevenYears, cheapThrills, cykaBlyat)


@Composable
fun ListSongHorizontal(
    modifier: Modifier = Modifier,
    listSong: List<Song>,
    onClick: (Song) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Playlist",
                style = MaterialTheme.typography.displaySmall,
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Workspaces, contentDescription = null)
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = true
        ) {
            items(listSong) {
                CardImageSong(song = it, onClick = onClick)
            }
        }
    }
}

@Composable
fun CardImageSong(modifier: Modifier = Modifier, song: Song, onClick: (Song) -> Unit) {
    Card(
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 24.dp)
            .size(140.dp, 180.dp)
            .clickable {
                onClick(song)
            }
    ) {
        Image(
            painter = painterResource(id = song.banner),
            contentDescription = song.singer,
            contentScale = ContentScale.Crop
        )
    }
}

fun LazyListScope.listSongVertical(modifier: Modifier = Modifier, onClick: (Song) -> Unit) {

    items(listSong) {
        CardSong(song = it, onClick = onClick)
    }

}

@Composable
fun CardSong(modifier: Modifier = Modifier, song: Song, onClick: (Song) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp)
            .clickable {
                onClick(song)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Image(
            painter = painterResource(id = song.banner),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(32.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
            Text(
                text = song.singer,
                maxLines = 1,
                modifier = Modifier.alpha(0.5f),
                overflow = TextOverflow.Ellipsis

            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListSong(modifier: Modifier = Modifier, onClick: (Song) -> Unit) {
    //  data
    val playingSong by remember { mutableStateOf(demons) }

    //FIXME     cant nested scroll
//    Column(modifier = modifier.fillMaxWidth()) {
//        ListSongHorizontal(listSong = listSong, onClick = onClick)
//        ListSongVertical(onClick = onClick)
//    }
    //  ui: nested scroll
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        userScrollEnabled = true
    ) {
        item {
            CardPlayingSong(song = playingSong, onClick = onClick)
        }
        item() {
            ListSongHorizontal(listSong = listSong, onClick = onClick)
        }
        listSongVertical(onClick = onClick)
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .shadow(40.dp, RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp))
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp)
            )
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(vertical = 24.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBottomBar(icon = Icons.Default.Home, enabled = true)
        IconBottomBar(icon = Icons.Default.Search)
        IconBottomBar(icon = Icons.Default.FilterNone)
        IconBottomBar(icon = Icons.Default.Person)
    }
}

@Composable
fun IconBottomBar(modifier: Modifier = Modifier, icon: ImageVector, enabled: Boolean = false) {
    IconButton(modifier = Modifier.alpha(if (enabled) 1f else 0.5f), onClick = { /*TODO*/ }) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

/*TODO
        _app cannot work yet
        _coming soon in future
FIXME
        _hoisting state
* */

























