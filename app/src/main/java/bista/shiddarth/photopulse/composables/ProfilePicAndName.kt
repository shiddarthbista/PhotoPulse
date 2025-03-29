package bista.shiddarth.photopulse.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
        } else InitialAvatar(firstName, lastName)

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "$firstName $lastName",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
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