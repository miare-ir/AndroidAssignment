package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.presentation.models.UiTopScorers

@Composable
fun TeamHeader(
    modifier: Modifier = Modifier,
    scorers: UiTopScorers,
) {
    TeamHeaderImpl(
        modifier = modifier,
        scorers = scorers
    )
}

@Composable
private fun TeamHeaderImpl(
    modifier: Modifier,
    scorers: UiTopScorers
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${scorers.league.name} - ${scorers.league.country}")
            Text(
                text = "Rank: ${scorers.league.rank} | Matches: ${scorers.league.totalMatches}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}