package bista.shiddarth.photopulse.model

import androidx.annotation.DrawableRes

data class Profile(
    val username: String,
    val isVerified: Boolean,
    @DrawableRes val profilePictureUrl: Int,
    val postsCount: Int,
    val followersCount: Int,
    val followingCount: Int,
    val photos: List<Int>,
    val taggedPhotos: List<Int>
)
