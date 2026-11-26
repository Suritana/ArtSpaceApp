package com.example.artspaceapp

import android.content.res.Configuration
import androidx.compose.ui.text.font.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val artistRes: Int,
    @StringRes val yearRes: Int
)

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    val total = 3
    var index by rememberSaveable { mutableStateOf(0) }

    val artwork = when (index) {
        0 -> Artwork(R.drawable.art1, R.string.title1, R.string.artist1, R.string.year1)
        1 -> Artwork(R.drawable.art2, R.string.title2, R.string.artist2, R.string.year2)
        else -> Artwork(R.drawable.art3, R.string.title3, R.string.artist3, R.string.year3)
    }
    val onPrevious = { index = (index - 1 + total) % total }
    val onNext = { index = (index + 1) % total }

    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ArtworkImage(
                artwork = artwork,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ArtworkInfo(artwork)
                Spacer(modifier = Modifier.height(24.dp))
                NavigationButtons(onPrevious = onPrevious, onNext = onNext)
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArtworkImage(
                artwork = artwork,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            ArtworkInfo(artwork)
            Spacer(modifier = Modifier.height(24.dp))
            NavigationButtons(onPrevious = onPrevious, onNext = onNext)
        }
    }
}

@Composable
fun ArtworkImage(artwork: Artwork, modifier: Modifier = Modifier) {
    Surface(shadowElevation = 8.dp, modifier = modifier) {
        Image(
            painter = painterResource(artwork.imageRes),
            contentDescription = stringResource(artwork.titleRes),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun ArtworkInfo(artwork: Artwork, modifier: Modifier = Modifier) {
    val artist = stringResource(artwork.artistRes)
    val year = stringResource(artwork.yearRes)
    Column(
        modifier = modifier
            .background(Color(0xFFECEBF4))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(artwork.titleRes),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = buildAnnotatedString {
                append("$artist (")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append(year) }
                append(")")
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun NavigationButtons(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}