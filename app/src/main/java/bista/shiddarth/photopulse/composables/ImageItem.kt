package bista.shiddarth.photopulse.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bista.shiddarth.photopulse.R

@Composable
fun ImageItem(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(2f / 3f)
            .height(80.dp)
            .padding(1.dp)
    )
}

@Preview
@Composable
fun ImageItemPreview(){
    ImageItem(R.drawable.date)
}