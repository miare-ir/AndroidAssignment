package ir.miare.androidcodechallenge.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
    )
}

@Preview(showBackground = true)
@Composable
private fun TitlePreview() {
    Title(
        text = "Leagues & Players"
    )
}