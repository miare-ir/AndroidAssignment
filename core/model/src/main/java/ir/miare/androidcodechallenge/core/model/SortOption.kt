package ir.miare.androidcodechallenge.core.model

enum class SortOption(val value: String) {
    TEAM_AND_LEAGUE_RANK("Team & league ranking"),
    MOST_GOALS("Most goals scored by a player"),
    AVERAGE_GOALS_PER_MATCH("Average goal per match in a league"),
    NONE("None")
}