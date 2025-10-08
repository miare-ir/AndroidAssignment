package ir.miare.androidcodechallenge.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.miare.androidcodechallenge.core.model.League
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.model.stableKey
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val pagingData = viewModel.pagingUi.collectAsLazyPagingItems()

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        sortOption = sortOption,
        pagingData = pagingData,
        onSortChanged = viewModel::onSortChanged,
        onFollowClick = viewModel::onFollowClick,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    sortOption: SortOption,
    pagingData: LazyPagingItems<LeagueDisplayItem>,
    onSortChanged: (SortOption) -> Unit,
    onBackClick: () -> Unit,
    onFollowClick: (Player)-> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Leagues & Players",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    SortCompact(sortOption = sortOption, onSortChanged = onSortChanged)
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Error -> {
                    ErrorPane(
                        message = uiState.message,
                        onRetry = { pagingData.refresh() }
                    )
                }

                is HomeUiState.Success -> {
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                for (index in 0 until pagingData.itemCount) {
                    val item = pagingData.peek(index) ?: continue
                    when (item) {
                        is LeagueDisplayItem.Header -> {
                            stickyHeader(key = "hdr-$index") {
                                LeagueHeaderCard(
                                    title = "${item.league.name} • ${item.league.country}"
                                )
                            }
                        }

                        is LeagueDisplayItem.PlayerItem -> {
                            item(
                                key = item.player.stableKey(),
                                contentType = "player"
                            ) {
                                PlayerCard(
                                    item = item,
                                    onFollowClick = onFollowClick
                                )

                            }
                        }
                    }
                }


                when (val append = pagingData.loadState.append) {
                    is androidx.paging.LoadState.Loading -> {
                        item("append-loading") {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is androidx.paging.LoadState.Error -> {
                        item("append-error") {
                            Text(
                                text = append.error.message ?: "Error loading more",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> Unit
                }
            }


            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}

@Composable
private fun LeagueHeaderCard(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun PlayerCard(
    item: LeagueDisplayItem.PlayerItem,
    onFollowClick: (Player)-> Unit = {}
    ) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        .height(40.dp)
                        .fillMaxWidth(0.0f)
                        .then(Modifier)
                )

                Text(
                    text = item.player.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                )

                AssistChip(
                    onClick = {
                        onFollowClick(item.player)
                    },
                    label = { Text(
                        text = if (item.player.isFollowed) "Followed" else "Follow"
                    ) },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )
            }

            Spacer(Modifier.height(10.dp))
            Divider()
            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatPill(title = "Goals", value = "${item.player.totalGoal}")
                StatPill(title = "Team", value = item.player.team.name)
                item.avgGoals?.let {
                    StatPill(
                        title = "Avg/Match",
                        value = String.format("%.2f", it)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatPill(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "$title: ",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun ErrorPane(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp),
        )
        val tryAgainString = buildAnnotatedString {
            append("Try again ")
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append("Refresh")
            }
        }
        Text(
            text = tryAgainString,
            modifier = Modifier
                .clickable { onRetry() }
                .padding(4.dp)
        )
    }
}

@Composable
private fun SortCompact(
    sortOption: SortOption,
    onSortChanged: (SortOption) -> Unit
) {
    Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 8.dp)) {
        Text(
            "Sort",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            SortOption.entries.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onSortChanged(option) }
                        .padding(start = 6.dp)
                ) {
                    RadioButton(
                        selected = sortOption == option,
                        onClick = { onSortChanged(option) }
                    )
                    Text(
                        text = option.name.replace('_', ' ')
                            .lowercase()
                            .replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}


@Composable
private fun demoPagingItems(): LazyPagingItems<LeagueDisplayItem> {
    val demo = listOf(
        LeagueDisplayItem.Header(
            league = League(
                name = "La Liga",
                country = "Spain",
                rank = 2,
                totalMatches = 38
            )
        ),
        LeagueDisplayItem.PlayerItem(
            player = Player(
                name = "Jude Bellingham",
                totalGoal = 19,
                team = Team(name = "Real Madrid", rank = 1)
            ),
            league = League(
                name = "La Liga", country = "Spain", rank = 2, totalMatches = 38
            ),
            avgGoals = 0.50f
        ),
        LeagueDisplayItem.PlayerItem(
            player = Player(
                name = "Robert Lewandowski",
                totalGoal = 23,
                team = Team(name = "Barcelona", rank = 2)
            ),
            league = League(
                name = "La Liga", country = "Spain", rank = 2, totalMatches = 38
            ),
            avgGoals = 0.61f
        ),
        LeagueDisplayItem.Header(
            league = League(
                name = "Premier League",
                country = "England",
                rank = 1,
                totalMatches = 38
            )
        ),
        LeagueDisplayItem.PlayerItem(
            player = Player(
                name = "Erling Haaland",
                totalGoal = 27,
                team = Team(name = "Man City", rank = 1)
            ),
            league = League(
                name = "Premier League", country = "England", rank = 1, totalMatches = 38
            ),
            avgGoals = 0.71f
        )
    )
    return flowOf(PagingData.from(demo)).collectAsLazyPagingItems()
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState.Success(emptyList()),
        sortOption = SortOption.NONE,
        pagingData = demoPagingItems(),
        onSortChanged = {},
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    HomeScreen(
        uiState = HomeUiState.Error("Network error. Please try again."),
        sortOption = SortOption.NONE,
        pagingData = demoPagingItems(),
        onSortChanged = {},
        onBackClick = {}
    )
}
