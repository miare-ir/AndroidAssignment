package ir.miare.androidcodechallenge.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.miare.androidcodechallenge.core.model.SortOption
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSelectionBottomSheet(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onConfirm: (SortOption) -> Unit = {},
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Hidden
        }
    )
    val scope = rememberCoroutineScope()

    var selectedSortOption by
    rememberSaveable { mutableStateOf(SortOption.NONE) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onCancel()
        },
        sheetState = sheetState,
        dragHandle = null
    ) {
        SortSelectionScreen(
            modifier = modifier,
            onCancel = onCancel,
            selectedSortItem = selectedSortOption,
            onSortSelected = {
                scope.launch {
                    selectedSortOption = it
                    onConfirm(selectedSortOption)
                }
            },
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSelectionScreen(
    modifier: Modifier = Modifier,
    selectedSortItem: SortOption,
    onSortSelected: (SortOption) -> Unit = {},
    onCancel: () -> Unit = {},
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choose sort",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Day Selection List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val items = SortOption.entries
                items(items.size) { item ->
                    SortItem(
                        sortOption = SortOption.entries[item],
                        isSelected = selectedSortItem == SortOption.entries[item],
                        onSortSelected = onSortSelected
                    )

                    if (item < items.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 1.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("SortSelection button"),
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    )
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        }
    }
}

@Composable
private fun SortItem(
    sortOption: SortOption,
    isSelected: Boolean,
    onSortSelected: (SortOption) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSortSelected(sortOption) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            modifier = Modifier
                .size(16.dp),
            imageVector = Icons.Default.Menu,
            contentDescription = "TextFieldPrefixImage"
        )

        Spacer(modifier = Modifier.width(10.dp))


        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {


            Text(
                text = sortOption.value,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleSmall,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = CircleShape
                )
                .padding(3.5.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            CircleShape
                        )
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun SortSelectionScreenPreview() {

    SortSelectionScreen(
        modifier = Modifier,
        onSortSelected = {},
        selectedSortItem = SortOption.NONE,
        onCancel = {},
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun SortItemPreview() {
    SortItem(
        sortOption = SortOption.NONE,
        isSelected = false,
        onSortSelected = {}
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun SortItemSelectedPreview() {
    SortItem(
        sortOption = SortOption.NONE,
        isSelected = true,
        onSortSelected = {}
    )
}