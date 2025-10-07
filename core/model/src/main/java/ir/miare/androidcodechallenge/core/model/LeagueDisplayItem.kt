package ir.miare.androidcodechallenge.core.model

sealed class LeagueDisplayItem{
    data class Header(val league: League): LeagueDisplayItem()
    data class PlayerItem(val player: Player): LeagueDisplayItem()
}
