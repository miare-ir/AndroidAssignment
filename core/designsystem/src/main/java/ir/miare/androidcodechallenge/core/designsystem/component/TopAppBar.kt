package ir.miare.androidcodechallenge.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    @DrawableRes titleImage: Int,
    modifier: Modifier = Modifier,
    bgColor: Color,
) {

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(titleImage),
                    contentDescription = "TopAppBar Title Image",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .height(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = bgColor
        ),
        modifier = modifier.testTag("AppTopAppBar"),
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun TopAppBarPreview() {
    AppTopAppBar(
        titleImage = android.R.drawable.ic_dialog_info,
        bgColor = MaterialTheme.colorScheme.primary
    )
}