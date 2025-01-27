package bista.shiddarth.photopulse.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InteractionButtons(
    likes: Int,
    comments: Int,
    shares: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }
    }
}

@Composable
fun InteractionButton(
    icon: ImageVector,
    count: Int,
    onClick: () -> Unit
) {
    var clickCount by rememberSaveable { mutableIntStateOf(count) }
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.Black),
    ) {
        IconButton(onClick = {
            isClicked = !isClicked
            if (isClicked) {
                clickCount++
            } else {
                clickCount--
            }
        }) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isClicked) Color.Red else Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            text = clickCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.offset(y = (-9).dp)
        )
    }
}

@Preview
@Composable
fun InteractionButtonPreview() {
    InteractionButton(Icons.Filled.Favorite, 2) { }
}