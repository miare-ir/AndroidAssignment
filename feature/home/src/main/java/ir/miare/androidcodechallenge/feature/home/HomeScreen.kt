package ir.miare.androidcodechallenge.feature.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.model.stableKey
import ir.miare.androidcodechallenge.core.ui.EmptySection
import ir.miare.androidcodechallenge.core.ui.LoadingSection
import ir.miare.androidcodechallenge.core.ui.PlayerCard
import ir.miare.androidcodechallenge.core.ui.Title

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val pagingData = viewModel.pagingUi.collectAsLazyPagingItems()

    val listState = rememberLazyListState()
    var showSortSelectionBottomSheet by remember { mutableStateOf(false) }

    val refreshState = pagingData.loadState.refresh
    LaunchedEffect(sortOption, refreshState) {
        if (refreshState is LoadState.NotLoading && pagingData.itemCount > 0) {
            listState.animateScrollToItem(0)
        }
    }

    if (showSortSelectionBottomSheet) {
        SortSelectionBottomSheet(
            sortOption = sortOption,
            onCancel = {
                showSortSelectionBottomSheet = false
            },
            onConfirm = { sortOption ->
                showSortSelectionBottomSheet = false
                viewModel.onSortChanged(sortOption)
            }
        )
    }

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        sortOption = sortOption,
        pagingData = pagingData,
        onFollowClick = viewModel::onFollowClick,
        onSortBarClick = {
            showSortSelectionBottomSheet = true
        },
        listState = listState
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    sortOption: SortOption,
    pagingData: LazyPagingItems<LeagueDisplayItem>,
    onSortBarClick: () -> Unit = {},
    onFollowClick: (Player) -> Unit = {},
    listState: LazyListState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(Modifier.height(10.dp))

        Title(
            modifier,
            text = "Leagues & Players"
        )

        Spacer(Modifier.height(20.dp))

        SortBar(
            modifier = modifier.fillMaxWidth(),
            onSortBarClick = onSortBarClick,
            sortOption = sortOption,
        )

        when (uiState) {
            is HomeUiState.Loading -> {
                LoadingSection()
            }

            is HomeUiState.Empty -> {
                EmptySection(
                    text = "There is no data to show"
                )
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
            items(
                count = pagingData.itemCount,
                key = { index ->
                    when (val item = pagingData.peek(index)) {
                        is LeagueDisplayItem.Header -> "hdr-${item.league.name}"
                        is LeagueDisplayItem.PlayerItem -> "ply-${item.player.stableKey()}"
                        else -> "row-$index"
                    }
                }
            ) { index ->
                val item = pagingData[index] ?: return@items
                when (item) {
                    is LeagueDisplayItem.Header -> {
                        LeagueHeaderCard(
                            title = "${item.league.name} • ${item.league.country}"
                        )
                    }

                    is LeagueDisplayItem.PlayerItem -> {
                        PlayerCard(
                            item = item.player,
                            onFollowClick = onFollowClick
                        )
                    }
                }
            }

            item {
                when (pagingData.loadState.append) {
                    is LoadState.Loading -> {
                        PaginationLoadingIndicator()
                    }

                    is LoadState.Error -> {
                        val error = (pagingData.loadState.append as LoadState.Error).error
                        ErrorPane(
                            message = error.message ?: "Error happened",
                            onRetry = { pagingData.refresh() }
                        )
                    }

                    else -> {}
                }
            }
        }
        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}

@Composable
fun PaginationLoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
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
fun SortBar(
    modifier: Modifier = Modifier,
    onSortBarClick: () -> Unit = {},
    sortOption: SortOption,
) {

    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clickable {
                onSortBarClick()
            },
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Sort icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Tap to sort",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = sortOption.value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState.Success(emptyList()),
        sortOption = SortOption.NONE,
        pagingData = demoPagingItems(),
        listState = rememberLazyListState()
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    HomeScreen(
        uiState = HomeUiState.Error("Network error. Please try again."),
        sortOption = SortOption.NONE,
        pagingData = demoPagingItems(),
        listState = rememberLazyListState()
    )
}
