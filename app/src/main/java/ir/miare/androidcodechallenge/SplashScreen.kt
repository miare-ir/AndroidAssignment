package ir.miare.androidcodechallenge

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {
    val dark = isSystemInDarkTheme()

    val gradientColors = if (dark) {
        listOf(
            MaterialTheme.colorScheme.surfaceContainerHighest,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary
        )
    } else {
        listOf(
            MaterialTheme.colorScheme.surfaceContainerLowest,
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary
        )
    }

    val inf = rememberInfiniteTransition(label = "bg")
    val shift by inf.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shift"
    )

    Box(
        modifier = Modifier
            .testTag("Splash Screen")
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, shift),
                    end = Offset(shift, 0f)
                )
            )
            .padding(24.dp)
    ) {

        SoftBlob(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.TopEnd)
                .offset(x = 24.dp, y = (-24).dp),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        )

        SoftBlob(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.BottomStart)
                .offset((-24).dp, 24.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.12f)
        )

        AppName()

        Loading()
    }
}

@Composable
fun AppName(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlowingRotatingBall()

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Fooootball",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Scores · Stats · Highlights",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun BoxScope.Loading(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Loading…",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 28.dp)
    )
}

@Composable
private fun GlowingRotatingBall() {
    val inf = rememberInfiniteTransition(label = "ball")

    val rotation by inf.animateFloat(
        initialValue = -10f,
        targetValue = 370f,
        animationSpec = infiniteRepeatable(tween(2200, easing = FastOutSlowInEasing)),
        label = "rotation"
    )

    val pulse by inf.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val glow by inf.animateFloat(
        initialValue = 0.65f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val size = 120.dp
    val ringSize = 170.dp

    val color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.16f)
    Box(
        modifier = Modifier
            .size(ringSize)
            .drawBehind {
                val r = (size.toPx() * pulse)
                drawCircle(
                    color = color,
                    radius = r
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .size(size)
                .rotate(rotation)
                .drawBehind {
                    val radius = size.toPx() * 0.95f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color.copy(alpha = 0.45f * glow),
                                Color.Transparent
                            )
                        ),
                        radius = radius
                    )
                },
            shape = CircleShape,
            tonalElevation = 10.dp,
            shadowElevation = 16.dp,
            color = color.copy(alpha = 0.9f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.ic_ball),
                    contentDescription = "Football",
                    modifier = Modifier.size(56.dp)
                )
            }
        }
    }
}

@Composable
private fun SoftBlob(modifier: Modifier, color: Color) {
    Box(
        modifier = modifier.drawBehind {
            val radius = size.maxDimension / 2f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color, Color.Transparent)
                ),
                radius = radius,
                center = center
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SplashPreviewLight() {
    MaterialTheme {
        SplashScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        SplashScreen()
    }
}
