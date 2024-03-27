package dev.vishnuv.globeshader

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import dev.vishnuv.globeshader.shader.FibonacciSphere_SHADER
import dev.vishnuv.globeshader.ui.theme.GlobeShaderTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GlobeShaderTheme {
                // A surface container using the 'background' color from the theme
                FibonacciSphere()

            }
        }
    }
}


@Composable
fun FibonacciSphere() {
    val scope = rememberCoroutineScope()
    val timeMs = remember { mutableFloatStateOf(0f) }
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                timeMs.floatValue = (System.currentTimeMillis() % 100_000L) / 1_000f
                delay(10)
            }
        }
    }
    val shader = RuntimeShader(FibonacciSphere_SHADER.trimIndent())

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "size",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                shader.setFloatUniform("time", timeMs.floatValue)
                renderEffect = RenderEffect
                    .createShaderEffect(shader)
                    .asComposeRenderEffect()
            }, content = {}
    )
}