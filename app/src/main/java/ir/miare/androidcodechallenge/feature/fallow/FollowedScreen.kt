package ir.miare.androidcodechallenge.feature.fallow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.core.common.component.PlayerCard

@Composable
fun FollowedScreen(
    viewModel: FollowedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FollowedScreen(
        uiState = uiState,
        onFollowClicked = viewModel::onFollowClicked
    )
}

@Composable
internal fun FollowedScreen(
    uiState: FollowedUiState,
    onFollowClicked: (String, Boolean) -> Unit,
) {
    when (val state = uiState) {
        is FollowedUiState.Loading -> {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator()
                Text(
                    "  Loading followed players...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        is FollowedUiState.Error -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                Row(Modifier.padding(top = 8.dp)) {
                    Button(onClick = { /* no-op retry, flow resumes automatically */ }) { Text("Retry") }
                }
            }
        }

        is FollowedUiState.Success -> {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                item { Text("Followed Players", style = MaterialTheme.typography.titleMedium) }
                if (state.data.isEmpty()) {
                    item { Text("You aren't following any players yet. Tap the star to follow.") }
                } else {
                    items(state.data) { p ->
                        PlayerCard(p) { follow ->
                            onFollowClicked(
                                p.playerId,
                                follow
                            )
                        }
                    }
                }
            }
        }
    }
}