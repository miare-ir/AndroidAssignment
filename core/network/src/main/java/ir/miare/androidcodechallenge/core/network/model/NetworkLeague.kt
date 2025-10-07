package ir.miare.androidcodechallenge.core.network.model

data class NetworkLeague(
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int,
)
