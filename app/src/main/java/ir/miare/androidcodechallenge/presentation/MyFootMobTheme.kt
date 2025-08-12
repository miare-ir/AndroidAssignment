package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = Color(0xFFD71A21),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0E0E0),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFFF2F2F2),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFC2C2C2),
    onSecondaryContainer = Color.Black,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    surface = Color(0xFFF0F0F0),
    onSurface = Color(0xFF333333),
    error = Color(0xFF800808),
    onError = Color.White,
    surfaceVariant = Color.Gray.copy(alpha = 0.7f),
    onSurfaceVariant = Color.White,
    surfaceTint = Color.DarkGray,
    tertiary = Color(0xFF54C5D0),
    onTertiary = Color(0xFFEB7400),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFD71A21),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2C2C2C),
    onPrimaryContainer = Color.White,
    secondary = Color.Black.copy(alpha = 0.7f),
    onSecondary = Color(0xFFF2F2F2),
    secondaryContainer = Color(0xFFC2C2C2),
    onSecondaryContainer = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF35353A),
    onSurface = Color(0xFFE0E0E0),
    error = Color(0xFFB36B6B),
    onError = Color.White,
    surfaceVariant = Color.Gray.copy(alpha = 0.7f),
    onSurfaceVariant = Color.White,
    surfaceTint = Color.LightGray,
    tertiary = Color(0xFF54C5D0),
    onTertiary = Color(0xFFEB7400),
)

private val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
    ),
    displayMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
    ),
    titleLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )
)

@Composable
fun MyFootMobTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )

}