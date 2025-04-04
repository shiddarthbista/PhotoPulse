package bista.shiddarth.photopulse.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R

data class Comments(val firstNane: String,val lastName:String, val comment: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageButton(
    count: Int,
    comments: MutableList<Comments>,
    onClick: () -> Unit
) {
    val clickCount by rememberSaveable { mutableIntStateOf(count) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var newComment by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${comments.size} Comments",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    comments.forEach { comment ->
                        CommentItem(comment)
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    CommentInputField(
                        value = newComment,
                        onValueChange = { newComment = it },
                        onCommentSubmit = {
                            if (newComment.text.isNotBlank()) {
                                comments.add(Comments("Shiddarth", "Bista", newComment.text))

                                newComment = TextFieldValue("")
                                keyboardController?.hide()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comments) {
    var likeCount by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialAvatar(comment.firstNane,comment.lastName)
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${comment.firstNane} ${comment.lastName}", fontWeight = FontWeight.Bold)
            Text(text = comment.comment, fontSize = 14.sp, color = Color.Gray)
        }
        LikeIcon(25, likeCount, 15) { likeCount = it }
    }
}

@Composable
fun CommentInputField(
    value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, onCommentSubmit: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray, CircleShape)
                .padding(8.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onSend = {
                if (value.text.isNotBlank()) {
                    onCommentSubmit()
                    keyboardController?.hide()
                }
            })
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { /* Handle send comment */ }) {
            Icon(painter = painterResource(id = R.drawable.ic_comment), contentDescription = "Send")
        }
    }
}