package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.model.Player

@Composable
fun LeagueSection(
    leagueData: LeagueData,
    onFollowClick: (Player) -> Unit
) {
    Column {
        Text(
            text = "${leagueData.league.name} (${leagueData.league.country})",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        leagueData.players.take(3).forEach { player ->
            PlayerItem(player = player, onFollowClick = onFollowClick)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
