package ir.miare.androidcodechallenge.feature.home

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class HomeUiStatePreviewParameterProvider : PreviewParameterProvider<HomeUiState> {
    override val values: Sequence<HomeUiState> = sequenceOf(
        HomeUiState.Success(
            displayItems = emptyList()
        ),
    )
}
