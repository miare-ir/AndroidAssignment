package ir.miare.androidcodechallenge.core.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.core.model.PlayerWithDetails

@Composable
fun PlayerCard(
    player: PlayerWithDetails,
    onToggleFollow: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (player.imageUrl.isNullOrBlank()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_player_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                )
            } else {
                AsyncImage(
                    model = player.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = player.playerName, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${player.teamName} • ${player.leagueName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Goals: ${player.goalsScored}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            val selected = player.isFollowed
            FilterChip(
                selected = selected,
                onClick = { onToggleFollow(!selected) },
                label = { Text(if (selected) "Unfollow" else "Follow") },
                leadingIcon = { Icon(imageVector = Icons.Default.Star, contentDescription = null) }
            )
        }
    }
}