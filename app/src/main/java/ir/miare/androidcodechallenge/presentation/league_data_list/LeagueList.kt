package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.LeagueData

@Composable
fun LeagueList(leagues: List<LeagueData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        leagues.forEach { leagueData ->
            item {
                LeagueSection(leagueData)
            }
        }
    }
}
