package bista.shiddarth.photopulse.model

data class Post(
    val imageId:Int,
    val imageResourceId: Int,
    val uploadedBy: String,
    val uploadedAt: String,
    val description: String,
    val hashtags: List<String> = emptyList()
)
