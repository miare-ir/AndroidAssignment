package ir.miare.androidcodechallenge.core.model

data class Player(
    val name: String,
    val team: Team,
    val totalGoal: Int,
    var isFollowed: Boolean = false
)

fun Player.stableKey(): String = buildString {
    append(normalize(team.name))
    append("::")
    append(normalize(name))
}

private fun normalize(value: String): String =
    value.trim().lowercase()
