package bista.shiddarth.photopulse.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R
import bista.shiddarth.photopulse.ui.theme.fancyFont

@Composable
fun RowImageItem(imageRes: Int) {
    Column(
        modifier = Modifier.fillMaxWidth() ,horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null
        )
        Text(
            text = "asd",
            fontSize = 20.sp,
            color = Color.Red,
            fontFamily = fancyFont,
        )
    }
}

@Preview
@Composable
fun RowImageItemPreview(){
    RowImageItem(imageRes = R.drawable.date)
}