package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTeam

@Composable
fun PlayerInfoBottomSheetContent(
    modifier: Modifier = Modifier,
    player: UiPlayer,
    onCloseClick: () -> Unit,
) {
    PlayerInfoBottomSheetContentImpl(
        modifier = modifier,
        player = player,
        onCloseClick = onCloseClick,
    )
}

@Composable
private fun PlayerInfoBottomSheetContentImpl(
    modifier: Modifier = Modifier,
    player: UiPlayer,
    onCloseClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = player.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.player_sheet_team, player.team.name),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stringResource(R.string.player_sheet_total_goals, player.totalGoal),
            style = MaterialTheme.typography.bodyLarge,
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onCloseClick
        ) {
            Text(
                text = stringResource(R.string.player_sheet_close)
            )
        }
    }
}

private val fakeData = UiPlayer(
    name = "Lionel Messi",
    team = UiTeam(
        name = "Paris Saint-Germain",
        rank = 32,
    ),
    totalGoal = 800
)

@Preview(showBackground = true)
@Composable
private fun PlayerInfoBottomSheetContentPreview() {
    AppTheme {
        PlayerInfoBottomSheetContent(
            player = fakeData,
            onCloseClick = {}
        )
    }
}