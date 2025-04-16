package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme
import ir.miare.androidcodechallenge.presentation.models.UiLeague
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTeam
import ir.miare.androidcodechallenge.presentation.models.UiTopScorers

@Composable
fun PlayersList(
    modifier: Modifier = Modifier,
    topScorers: List<UiTopScorers>,
    onPlayerClick: (UiPlayer) -> Unit,
) {
    PlayersListImpl(
        modifier = modifier,
        topScorers = topScorers,
        onPlayerClick = onPlayerClick,
    )
}

@Composable
private fun PlayersListImpl(
    modifier: Modifier = Modifier,
    topScorers: List<UiTopScorers>,
    onPlayerClick: (UiPlayer) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        topScorers.forEach {
            item {
                TeamHeader(scorers = it)
            }
            items(
                items = it.players
            ) { player ->
                PlayerItem(
                    modifier = Modifier.clickable {
                        onPlayerClick(player)
                    },
                    player = player,
                )
            }
        }
    }
}

private val fakeData = List(4) {
    UiTopScorers(
        players = List(3) { index ->
            UiPlayer(
                name = "Player $index",
                team = UiTeam(
                    name = "Team $index",
                    rank = index,
                ),
                totalGoal = index * 10,
            )
        },
        league = UiLeague(
            name = "League $it",
            country = "Country $it",
            rank = it,
            totalMatches = it * 100,
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayersListPreview() {
    AppTheme {
        PlayersList(
            topScorers = fakeData,
            onPlayerClick = {},
        )
    }
}