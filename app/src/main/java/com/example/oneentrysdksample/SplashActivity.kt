package com.example.oneentrysdksample

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.example.oneentrysdksample.ui.theme.OneEntrySDKSampleTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SplashScreen()
            }
        }
    }

    @Composable
    fun SplashScreen() {

        val alpha = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = true) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(1500)
            )
            delay(2000)
            startActivity(
                Intent(this@SplashActivity, MainActivity::class.java)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.alpha(alpha.value),
                painter = painterResource(id = R.drawable.oneentry),
                contentDescription = null
            )
        }
    }
}