package bista.shiddarth.photopulse

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import bista.shiddarth.photopulse.model.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition {keepSplashScreen}
        lifecycleScope.launch {
            delay(1000)
            keepSplashScreen = false
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PhotoPulseApp() }
    }
}

@Composable
fun PhotoItemList(imagesList: List<Image>) {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 56.dp)
    ) { page ->
        val actualPage = page % imagesList.size
        val photo = imagesList[actualPage]
        PhotoItem(resId = photo.resourceId)
    }
}

@Composable
fun PhotoItem(resId: Int) {
    val context = LocalContext.current as ComponentActivity

    context.enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(Color.BLACK),
        navigationBarStyle = SystemBarStyle.dark(Color.BLACK)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "Photo",
            modifier = Modifier.fillMaxSize(),
            //TODO: Decide Crop or Fit. Crop cuts image but gets full screen. Fit has empty spaces
            contentScale = ContentScale.Crop
        )
//        IconButton(
//            onClick = { /* Handle like action */ },
//            modifier = Modifier.align(Alignment.BottomEnd)
//        ) {
//            Icon(
//                imageVector = Icons.Default.FavoriteBorder,
//                contentDescription = "Like"
//            )
//        }
    }
}

@Preview
@Composable
fun PhotoItemPreview() {
    PhotoItem(resId = R.drawable.city)
}

@Preview
@Composable
fun PhotoItemListPreview(){
    PhotoItemList(imagesList = listOf(
        Image(1, R.drawable.city),
        Image(2, R.drawable.girlpar),
        Image(3, R.drawable.headphones),
        Image(4, R.drawable.moon)
    ))
}
