package ir.miare.androidcodechallenge.core.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val name: String,
    @SerialName("total_goal")
    val totalGoal: Int,
    val team: Team,
    @SerialName("image_url")
    val imageUrl: String? = null,
)