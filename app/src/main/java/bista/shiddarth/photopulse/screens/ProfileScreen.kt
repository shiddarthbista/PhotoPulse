package bista.shiddarth.photopulse.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R
import bista.shiddarth.photopulse.model.Profile
import com.rahad.riobottomnavigation.ui.theme.Purple40
import com.rahad.riobottomnavigation.ui.theme.Purple80

@Composable
@Preview
fun ProfilePreveiw() {
    ProfileScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val profile = Profile(
        username = "Shiddarth Bista",
        isVerified = true,
        R.drawable.avatar1,
        postsCount = 15,
        followersCount = 20,
        followingCount = 14,
        photos = listOf(
            R.drawable.sports2, R.drawable.sports4, R.drawable.sports5,
            R.drawable.water2, R.drawable.water4, R.drawable.water5,
            R.drawable.date, R.drawable.clock, R.drawable.mountain,
            R.drawable.sports2, R.drawable.sports4, R.drawable.sports5,
        ),
        taggedPhotos = listOf(R.drawable.mountain)
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }


    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Black
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = profile.username,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        if (profile.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_verified),
                                contentDescription = "Verified",
                                tint = Color.Green,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .background(color = Color.Black)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileHeaderSection(profile)
            Spacer(modifier = Modifier.height(16.dp))
            ProfileActionButtons()
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTabSection(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            // Show a different grid depending on which tab is selected
            when (selectedTabIndex) {
                0 -> PhotoGridSection(profile.photos)
                1 -> PhotoGridSection(profile.taggedPhotos)
            }
        }
    }
}

@Composable
fun ProfileHeaderSection(profile: Profile) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.date),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat(title = "Posts", value = profile.postsCount)
            ProfileStat(title = "Followers", value = profile.followersCount)
            ProfileStat(title = "Following", value = profile.followingCount)
        }
    }
}

@Composable
fun ProfileStat(title: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
        Text(text = title, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun ProfileActionButtons() {
    var isFollowing by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { isFollowing = !isFollowing },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if(isFollowing) Color.Black else Purple40,
            ),
            border = if (isFollowing) BorderStroke(0.dp, Color(0xFF74777F)) else BorderStroke(0.dp, Purple80)
        ) {
            if (isFollowing) {
                Text(text = "Following", color =  Purple80)
            } else Text(text = "Follow")
        }
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedButton(onClick = { /* Message action */ }, modifier = Modifier.weight(1f)) {
            Text(text = "Message")
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ProfileTabSection(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        R.drawable.ic_grid to "Posts",
        R.drawable.ic_tagged to "Tagged"
    )

    TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color.Black,
        indicator = { tabPositions ->
            val currentTabPosition = tabPositions[selectedTabIndex]
            val indicatorWidth = 80.dp
            val indicatorHeight = 4.dp

            val animatedOffset by animateDpAsState(
                targetValue = currentTabPosition.left + (currentTabPosition.width - indicatorWidth) / 2,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = ""
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomStart)
                    .offset(x = animatedOffset)
                    .width(indicatorWidth)
                    .height(indicatorHeight)
                    .background(Purple40, shape = RoundedCornerShape(2.dp))
            )
        }
    ) {
        tabs.forEachIndexed { index, (iconRes, contentDescription) ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = contentDescription
                    )
                }
            )
        }
    }
}

@Composable
fun PhotoGridSection(photos: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos) { photo ->
            Image(
                painter = painterResource(id = photo),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(2f / 3f)
                    .height(12.dp)
                    .padding(1.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}