package com.space.swagger.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.space.swagger.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

class SplashActivity : Activity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(1500)
            startActivity( Intent(this@SplashActivity, DashboardActivity::class.java))
            finish()
        }
    }
}
