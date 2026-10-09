package com.example.sleepsounds

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView

class MainActivity : Activity() {
    private lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(arrayOf("android.permission.POST_NOTIFICATIONS"), 1)
        }
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.mediaPlaybackRequiresUserGesture = false
        web.webChromeClient = WebChromeClient()
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
        startForegroundService(Intent(this, PlayService::class.java))
    }

    // Back button hides the app instead of closing it, so sound keeps playing.
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() { moveTaskToBack(true) }

    override fun onDestroy() {
        stopService(Intent(this, PlayService::class.java))
        web.destroy()
        super.onDestroy()
    }
}
