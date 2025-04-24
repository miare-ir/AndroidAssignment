package ir.miare.androidcodechallenge

enum class SortType(val title: String) {

    RANK("Team & league ranking"),
    MOST("Most goals scored by a player"),
    AVERAGE("Average goal per match in a league"),
    None("None");

    companion object {
        fun fromString(value: String?): SortType? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}