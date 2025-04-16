package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme

@Composable
fun MessageSection(
    modifier: Modifier = Modifier,
    isErrorMessage: Boolean = false,
    message: String,
) {
    MessageSectionImpl(
        modifier = modifier,
        isErrorMessage = isErrorMessage,
        message = message
    )
}

@Composable
private fun MessageSectionImpl(
    modifier: Modifier = Modifier,
    isErrorMessage: Boolean = false,
    message: String,
) {
    val CardColors = CardDefaults.cardColors().copy(
        containerColor = if (isErrorMessage) {
            MaterialTheme.colorScheme.errorContainer
        } else {
            MaterialTheme.colorScheme.primaryContainer
        }
    )
    Card(
        modifier = modifier,
        colors = CardColors
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageSectionPreview() {
    AppTheme {
        MessageSection(
            message = "This is a message",
            isErrorMessage = false
        )
    }
}