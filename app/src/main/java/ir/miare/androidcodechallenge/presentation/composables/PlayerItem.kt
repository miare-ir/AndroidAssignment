package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTeam

@Composable
fun PlayerItem(
    modifier: Modifier = Modifier,
    player: UiPlayer,
) {
    PlayerItemImpl(
        modifier = modifier,
        player = player,
    )
}

@Composable
private fun PlayerItemImpl(
    modifier: Modifier = Modifier,
    player: UiPlayer,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = player.team.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Card(
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = player.totalGoal.toString(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }

    }
}

private val samplePlayer = UiPlayer(
    name = "Lionel Messi",
    team = UiTeam(name = "Paris Saint-Germain", rank = 1),
    totalGoal = 800,
)

@Preview(showBackground = true)
@Composable
private fun PlayerItemPreview() {
    AppTheme {
        PlayerItem(
            modifier = Modifier,
            player = samplePlayer
        )
    }
}