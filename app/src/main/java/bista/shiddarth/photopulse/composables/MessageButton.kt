package bista.shiddarth.photopulse.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R

data class Comments(val commenter: String, val comment: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageButton(
    count: Int,
    comments: List<Comments>,
    onClick: () -> Unit
) {
    var clickCount by rememberSaveable { mutableIntStateOf(count) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick = {
            showBottomSheet = true
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

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                dragHandle = null
            ) {
                Box(modifier = Modifier.height(460.dp)) {
                    CommentModalContent(commentList = comments
                    ) { showBottomSheet = false }
                }
            }
        }
    }
}

@Composable
fun CommentModalContent(commentList: List<Comments>, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${commentList.size} comments",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp
            )

            IconButton(onClick = onClose ) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close Button")
            }
        }
        if (commentList.isEmpty()){
            Text(
                text = "No comments yet. Be the first to comment.",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}