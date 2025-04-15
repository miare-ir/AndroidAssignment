package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.core.domain.OrderBy
import ir.miare.androidcodechallenge.core.domain.Sort
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    orderState: OrderBy,
    onSortChange: (OrderBy) -> Unit,
) {
    SortSectionImpl(
        modifier = modifier,
        orderState = orderState,
        onSortChange = onSortChange,
    )
}

@Composable
private fun SortSectionImpl(
    modifier: Modifier = Modifier,
    orderState: OrderBy,
    onSortChange: (OrderBy) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            text = stringResource(id = R.string.name_ascending),
            selected = orderState == OrderBy.Name(sort = Sort.ASCENDING),
            onClick = {
                onSortChange(OrderBy.Name(sort = Sort.ASCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.name_descending),
            selected = orderState == OrderBy.Name(sort = Sort.DESCENDING),
            onClick = {
                onSortChange(OrderBy.Name(sort = Sort.DESCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.team_rank_ascending),
            selected = orderState == OrderBy.TeamRank(sort = Sort.ASCENDING),
            onClick = {
                onSortChange(OrderBy.TeamRank(sort = Sort.ASCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.team_rank_descending),
            selected = orderState == OrderBy.TeamRank(sort = Sort.DESCENDING),
            onClick = {
                onSortChange(OrderBy.TeamRank(sort = Sort.DESCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.average_score_per_match_ascending),
            selected = orderState == OrderBy.AverageScorePerMatch(sort = Sort.ASCENDING),
            onClick = {
                onSortChange(OrderBy.AverageScorePerMatch(sort = Sort.ASCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.average_score_per_match_descending),
            selected = orderState == OrderBy.AverageScorePerMatch(sort = Sort.DESCENDING),
            onClick = {
                onSortChange(OrderBy.AverageScorePerMatch(sort = Sort.DESCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.player_score_ascending),
            selected = orderState == OrderBy.PlayerScore(sort = Sort.ASCENDING),
            onClick = {
                onSortChange(OrderBy.PlayerScore(sort = Sort.ASCENDING))
            }
        )
        RadioButton(
            text = stringResource(id = R.string.player_score_descending),
            selected = orderState == OrderBy.PlayerScore(sort = Sort.DESCENDING),
            onClick = {
                onSortChange(OrderBy.PlayerScore(sort = Sort.DESCENDING))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SortSectionPreview() {
    AppTheme {
//        SortSection()
    }
}