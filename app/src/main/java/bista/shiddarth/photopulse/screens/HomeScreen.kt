package bista.shiddarth.photopulse.screens

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R
import bista.shiddarth.photopulse.model.Post
import bista.shiddarth.photopulse.ui.theme.PhotoPulseTheme
import bista.shiddarth.photopulse.ui.theme.fancyFont
import bista.shiddarth.photopulse.ui.theme.shadowsFontFamily
import androidx.compose.ui.graphics.Color.Companion as GraphicsColor

@Preview
@Composable
fun HomeScreen() {
    PhotoPulseTheme {
        val postsList = listOf(
            Post(
                1,
                R.drawable.city,
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
                R.drawable.avatar2,
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
                "Ramu",
                "Kaka",
                "2023/08/10",
                "Headphones",
                listOf("music", "beats", "drake", "almonds")
            ),
            Post(4, R.drawable.flowers, null, "Xiaoyou", "Shao", "2025/01/03", "Flower garden"),
            Post(
                5,
                R.drawable.moon,
                R.drawable.avatar1,
                "Neal",
                "Armstrong",
                "2025/01/13",
                "One small step for man , big step for manking",
                listOf("moon")
            )
        )
        PhotoItemList(postsList)
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

    context.enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(Color.BLACK),
        navigationBarStyle = SystemBarStyle.dark(Color.BLACK)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = post.imageResourceId),
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

            // Description
            Text(
                text = post.description,
                modifier = Modifier.padding(top = 2.dp),
                color = GraphicsColor.White,
                fontFamily = shadowsFontFamily
            )
            // Hashtags
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
    }
}

//@Preview
//@Composable
//fun PhotoItemPreview() {
//    PhotoItem(resId = R.drawable.city)
//}

@Preview
@Composable
fun PhotoItemListPreview() {
    val postsList = listOf(
        Post(
            1,
            R.drawable.city,
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
            "Selena",
            "Gomez",
            "2024/11/22",
            "Girl ",
            listOf("girldinner")
        ),
        Post(
            3,
            R.drawable.headphones,
            R.drawable.avatar1,
            "Ramu", "Kaka",
            "2023/08/10",
            "Headphones",
            listOf("music", "beats")
        ),
        Post(4, R.drawable.flowers, null, "Xiaoyou", "zing", "2025/01/03", "Flower garden"),
        Post(
            5,
            R.drawable.moon,
            R.drawable.avatar2,
            "Neal", "Armstrong",
            "2025/01/13",
            "One small step for man , big step for manking",
            listOf("moon")
        )
    )
    PhotoItemList(postsLists = postsList)
}

@Composable
fun ProfilePicAndName(profilePic: Int?, firstName: String, lastName: String, modifier: Modifier) {
    Row(
        modifier = Modifier
            .padding(top = 60.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (profilePic != null) {
            Image(
                painter = painterResource(id = profilePic),
                contentDescription = "$firstName $lastName's profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(GraphicsColor.Gray),
                contentScale = ContentScale.Crop
            )
        } else InitialAvatar(firstName, lastName)

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "$firstName $lastName",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = GraphicsColor.White
        )
    }
}

@Preview
@Composable
fun ProfilePicAndNamePreview() {
    ProfilePicAndName(
        profilePic = R.drawable.avatar1,
        firstName = "Shiddarth",
        lastName = "Bista",
        modifier = Modifier
    )
}

@Composable
fun InitialAvatar(firstName: String, lastName: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(GraphicsColor.Magenta),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${firstName.first()}${lastName.first()}",
            color = GraphicsColor.White,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun InitialAvatarPreview() {
    InitialAvatar(firstName = "Shiddarth", lastName = "Bista")
}
