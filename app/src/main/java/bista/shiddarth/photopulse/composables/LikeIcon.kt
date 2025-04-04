package bista.shiddarth.photopulse.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun LikeIcon(size: Int, likeCount: Int, fontSize: Int, onLikeCountChange: (Int) -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    var scale by remember { mutableFloatStateOf(1f) }
    val hapticFeedback = LocalHapticFeedback.current

    val color by animateColorAsState(if (isLiked) Color.Red else Color.White)

    LaunchedEffect(isLiked) {
        scale = 1.2f
        delay(300)
        scale = 1f
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            isLiked = !isLiked
            onLikeCountChange(if (isLiked) likeCount + 1 else likeCount - 1)
        }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like",
                tint = color,
                modifier = Modifier
                    .scale(scale)
                    .size(size.dp)
            )
        }

        Text(
            text = likeCount.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            fontSize = fontSize.sp,
            modifier = Modifier.offset(y = (-12).dp)
        )
    }
}