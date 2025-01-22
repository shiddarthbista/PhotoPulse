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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import bista.shiddarth.photopulse.model.Image
import bista.shiddarth.photopulse.ui.theme.PhotoPulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoPulseTheme {
                val imageList = listOf(
                    Image(1, R.drawable.city),
                    Image(2, R.drawable.girlpar),
                    Image(3, R.drawable.headphones),
                    Image(4, R.drawable.flowers),
                    Image(5, R.drawable.moon)
                )
                PhotoItemList(imageList)
            }
        }
    }
}

@Composable
fun PhotoItemList(imagesList: List<Image>) {
    // PagerState to keep track of the page index
    val pagerState = rememberPagerState(pageCount = { imagesList.size })

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        // Get the image for the current page
        val photo = imagesList[page]
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
