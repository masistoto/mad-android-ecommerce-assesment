package mad.app.madandroidtestsolutions.presentation.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mad.app.madandroidtestsolutions.R

@Composable
fun SplashScreen(navController: NavController) {

    Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
        val scale = remember {
            Animatable(0f)
        }

        // Animation
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                    targetValue = 0.7f,
                    // tween Animation
                    animationSpec = tween(
                            durationMillis = 800,
                            easing = {
                                OvershootInterpolator(4f).getInterpolation(it)
                            }))
            // Customize the delay time
            delay(3000L)
            navController.navigate("main_screen")
        }

        // Image
        Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()) {
            // Change the logo
            Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.scale(scale.value))
        }
    }
}