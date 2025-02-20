package bista.shiddarth.photopulse.model

data class Profile(
    val username: String,
    val isVerified: Boolean,
    val profilePictureUrl: String,
    val postsCount: Int,
    val followersCount: String,
    val followingCount: Int,
    val photos: List<String>
)
