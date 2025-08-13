import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ir.miare.androidcodechallenge.presentation.league_data_list.LeagueSection
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingSort
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingViewModel

@Composable
fun RankingScreen(viewModel: RankingViewModel = hiltViewModel()) {
    val leagues = viewModel.leaguesPagingFlow.collectAsLazyPagingItems()
    val currentSort by viewModel.sort.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Sort by: ${currentSort.displayName()}",
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                RankingSort.values().forEach { sortOption ->
                    DropdownMenuItem(
                        text = { Text(sortOption.displayName()) },
                        onClick = {
                            expanded = false
                            viewModel.setSort(sortOption)
                        }
                    )
                }
            }
        }

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
}