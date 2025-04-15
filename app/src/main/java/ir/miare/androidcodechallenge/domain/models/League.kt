package ir.miare.androidcodechallenge.domain.models

    data class League(
        val name: String,
        val country: String,
        val rank: Int,
        val totalMatches: Int,
    )
