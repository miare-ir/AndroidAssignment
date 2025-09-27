package ir.miare.androidcodechallenge.core.model

enum class SortMode(val storageKey: String) {
    DEFAULT("default"),
    LEAGUE_RANK("league_rank"),
    TEAM_RANK("team_rank"),
    GOALS_SCORED("goals_scored"),
    LEAGUE_GOAL_AVG("league_goal_avg");

    companion object {
        fun fromStorageKey(key: String?): SortMode =
            entries.find { it.storageKey == key } ?: DEFAULT
    }
}