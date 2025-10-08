package ir.miare.androidcodechallenge.feature.home

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.miare.androidcodechallenge.core.model.League
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team
import kotlinx.coroutines.flow.flowOf


@Composable
internal fun demoPagingItems(): LazyPagingItems<LeagueDisplayItem> {
    val demo = listOf(
        LeagueDisplayItem.Header(
            league = League(
                name = "La Liga",
                country = "Spain",
                rank = 2,
                totalMatches = 38
            )
        ),
        LeagueDisplayItem.PlayerItem(
            player = Player(
                name = "Jude Bellingham",
                totalGoal = 19,
                team = Team(name = "Real Madrid", rank = 1)
            ),
            league = League(
                name = "La Liga", country = "Spain", rank = 2, totalMatches = 38
            ),
            avgGoals = 0.50f
        ),
        LeagueDisplayItem.PlayerItem(
            player = Player(
                name = "Robert Lewandowski",
                totalGoal = 23,
                team = Team(name = "Barcelona", rank = 2)
            ),
            league = League(
                name = "La Liga", country = "Spain", rank = 2, totalMatches = 38
            ),
            avgGoals = 0.61f
        ),
        LeagueDisplayItem.Header(
            league = League(
                name = "Premier League",
                country = "England",
                rank = 1,
                totalMatches = 38
            )
        ),
        LeagueDisplayItem.PlayerItem(
            player = Player(
                name = "Erling Haaland",
                totalGoal = 27,
                team = Team(name = "Man City", rank = 1)
            ),
            league = League(
                name = "Premier League", country = "England", rank = 1, totalMatches = 38
            ),
            avgGoals = 0.71f
        )
    )
    return flowOf(PagingData.from(demo)).collectAsLazyPagingItems()
}
