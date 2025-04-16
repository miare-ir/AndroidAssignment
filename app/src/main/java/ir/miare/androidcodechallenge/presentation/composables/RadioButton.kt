package ir.miare.androidcodechallenge.presentation.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.core.presentation.theme.AppTheme

@Composable
fun RadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    onClick: () -> Unit = {},
) {
    SolidButtonImpl(
        modifier = modifier,
        selected = selected,
        text = text,
        onClick = onClick,
    )
}

@Composable
private fun SolidButtonImpl(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }
    )

    val colors =
        CardDefaults.cardColors().copy(
            containerColor = animatedColor
        )

    Card(
        colors = colors,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick
            )
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = text,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SolidButtonPreview() {
    AppTheme {
        RadioButton(
            text = "Label",
            selected = true,
            onClick = {},
        )
    }
}
