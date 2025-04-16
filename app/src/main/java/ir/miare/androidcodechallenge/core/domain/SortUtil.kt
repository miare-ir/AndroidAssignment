package ir.miare.androidcodechallenge.core.domain


sealed interface OrderBy {
    data class Name(val sort: Sort = Sort.ASCENDING) : OrderBy
    data class TeamRank(val sort: Sort = Sort.DESCENDING) : OrderBy
    data class AverageScorePerMatch(val sort: Sort = Sort.DESCENDING) : OrderBy
    data class PlayerScore(val sort: Sort = Sort.DESCENDING) : OrderBy
}

enum class Sort {
    ASCENDING,
    DESCENDING
}