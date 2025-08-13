package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.Player

@Composable
fun PlayerItem(
    player: Player,
    onFollowClick: (Player) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
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
            Button(onClick = { onFollowClick(player) }) {
                Text(if (player.isFollowed) "Unfollow" else "Follow")
            }
        }
    }
}
