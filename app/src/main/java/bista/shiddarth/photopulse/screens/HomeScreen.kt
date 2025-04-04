package bista.shiddarth.photopulse.screens

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bista.shiddarth.photopulse.R
import bista.shiddarth.photopulse.composables.InteractionButtons
import bista.shiddarth.photopulse.composables.ProfilePicAndName
import bista.shiddarth.photopulse.model.Post
import bista.shiddarth.photopulse.ui.theme.PhotoPulseTheme
import bista.shiddarth.photopulse.ui.theme.fancyFont
import bista.shiddarth.photopulse.ui.theme.shadowsFontFamily
import bista.shiddarth.photopulse.viewmodel.PostViewModel
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color.Companion as GraphicsColor
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun HomeScreen(viewModel: PostViewModel) {
    PhotoPulseTheme {
        PhotoItemList(viewModel.posts)
    }
}

@Composable
fun PhotoItemList(postsLists: List<Post>) {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )

    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) { page ->
        val actualPage = page % postsLists.size
        val post = postsLists[actualPage]
        PhotoItem(post)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhotoItem(post: Post) {
    val context = LocalContext.current as ComponentActivity
    var liked by remember { mutableStateOf(false) }


    context.enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(Color.BLACK),
        navigationBarStyle = SystemBarStyle.dark(Color.BLACK)
    )

    Box(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        liked = !liked
                    }
                )
            }
        ,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(post.imageUri ?: post.imageResourceId)
                .crossfade(true)
                .build(),
            contentDescription = "Photo",
            modifier = Modifier.fillMaxSize(),
            //TODO: Decide Crop or Fit. Crop cuts image but gets full screen. Fit has empty spaces
            contentScale = ContentScale.Crop
        )

        ProfilePicAndName(
            profilePic = post.profilePicId,
            firstName = post.firstName,
            lastName = post.lastName,
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomStart)
                .padding(16.dp, bottom = 26.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = post.uploadedAt,
                    color = GraphicsColor.White
                )
            }

            Text(
                text = post.description,
                modifier = Modifier.padding(top = 2.dp),
                color = GraphicsColor.White,
                fontFamily = shadowsFontFamily
            )

            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(bottom = 8.dp)

            ) {
                post.hashtags.forEach { hashtag ->
                    Box(
                        modifier = Modifier
                            .background(GraphicsColor.Gray, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "#$hashtag",
                            color = GraphicsColor.White,
                            fontFamily = fancyFont
                        )
                    }
                }
            }
        }

        InteractionButtons(
            likes = 2,
            comments = 3,
            shares = 2,
            onLikeClick = { /*TODO*/ },
            onCommentClick = { /*TODO*/ }) {
        }

        if (liked) {
            LikeAnimation()
        }
    }
}

@Composable
fun LikeAnimation() {
    var visible by remember { mutableStateOf(true) }

    if (visible) {
        LaunchedEffect(Unit) {
            delay(600)
            visible = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GraphicsColor.Black.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Liked",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview
@Composable
fun PhotoItemListPreview() {
    val postsList = listOf(
        Post(
            1,
            R.drawable.city,
            null,
            R.drawable.avatar1,
            "Shiddarth",
            "Bista",
            "2025/01/23",
            "City of Dreams",
            listOf("buildings")
        ),
        Post(
            2,
            R.drawable.girlpar,
            null,
            null,
            "Selena",
            "Gomez",
            "2024/11/22",
            "Girl ",
            listOf("girldinner")
        ),
        Post(
            3,
            R.drawable.headphones,
            null,
            R.drawable.avatar1,
            "Ramu", "Kaka",
            "2023/08/10",
            "Headphones",
            listOf("music", "beats")
        ),
        Post(4, R.drawable.flowers,null, null, "Xiaoyou", "zing", "2025/01/03", "Flower garden"),
        Post(
            5,
            R.drawable.moon,
            null,
            R.drawable.avatar2,
            "Neal", "Armstrong",
            "2025/01/13",
            "One small step for man , big step for manking",
            listOf("moon")
        )
    )
    PhotoItemList(postsLists = postsList)
}
