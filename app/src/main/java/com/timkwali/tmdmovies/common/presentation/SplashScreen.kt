package com.timkwali.tmdmovies.common.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.timkwali.tmdmovies.R
import com.timkwali.tmdmovies.common.utils.Constants
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        coroutineScope.launch {
            delay(Constants.SPLASH_DURATION)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}