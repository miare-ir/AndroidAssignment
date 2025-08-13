package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerItem(player: ir.miare.androidcodechallenge.data.model.Player) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = player.team.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Rank: ${player.team.rank}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
