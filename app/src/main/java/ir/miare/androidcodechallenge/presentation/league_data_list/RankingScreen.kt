package ir.miare.androidcodechallenge.presentation.league_data_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun RankingScreen(viewModel: RankingViewModel = hiltViewModel()) {
    val leagues = viewModel.leaguesPagingFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(leagues.itemCount) { index ->
            leagues[index]?.let { leagueData ->
                LeagueSection(leagueData)
            }
        }

        leagues.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { Text("Loading first page...") }
                }

                loadState.append is LoadState.Loading -> {
                    item { Text("Loading more...") }
                }

                loadState.append is LoadState.Error -> {
                    val e = leagues.loadState.append as LoadState.Error
                    item {
                        Column {
                            Text("Error: ${e.error.localizedMessage}")
                            Button(onClick = { retry() }) { Text("Retry") }
                        }
                    }
                }
            }
        }
    }
}