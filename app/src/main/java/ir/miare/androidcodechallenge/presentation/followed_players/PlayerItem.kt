package ir.miare.androidcodechallenge.presentation.followed_players

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.Player

@Composable
fun PlayerItem(player: Player, onUnfollow: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(player.name, style = MaterialTheme.typography.titleMedium)
            Text(
                "${player.team.name} • Goals: ${player.totalGoal}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = onUnfollow) {
            Icon(Icons.Default.Delete, contentDescription = "Unfollow")
        }
    }
}
