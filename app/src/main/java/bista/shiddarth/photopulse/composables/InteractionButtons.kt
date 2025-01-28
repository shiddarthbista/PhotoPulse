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
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R

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
            .padding(bottom = 50.dp, end = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LikeButton(count = 2) { }
            MessageButton(count = 3) { }
            ShareButton(count = 3) { }
        }
    }
}

@Composable
fun LikeButton(
    count: Int,
    onClick: () -> Unit
) {
    var clickCount by rememberSaveable { mutableIntStateOf(count) }
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = if (isClicked) Color.Red else Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            text = clickCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.displaySmall,
            fontSize = 15.sp,
            modifier = Modifier.offset(y = (-20).dp)
        )
    }
}

@Composable
fun MessageButton(
    count: Int,
    onClick: () -> Unit
) {
    var clickCount by rememberSaveable { mutableIntStateOf(count) }
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            text = clickCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.displaySmall,
            fontSize = 15.sp,
            modifier = Modifier.offset(y = (-20).dp)
        )
    }
}

@Composable
fun ShareButton(
    count: Int,
    onClick: () -> Unit
) {
    var clickCount by rememberSaveable { mutableIntStateOf(count) }
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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
                imageVector = Icons.Filled.Share,
                contentDescription = null,
                tint = if (isClicked) Color.Yellow else Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            text = clickCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.displaySmall,
            fontSize = 15.sp,
            modifier = Modifier.offset(y = (-20).dp)
        )
    }
}

@Preview
@Composable
fun InteractionButtonPreview() {
    LikeButton(2) { }
    MessageButton(count = 5) { }
    ShareButton(count = 5) { }
}