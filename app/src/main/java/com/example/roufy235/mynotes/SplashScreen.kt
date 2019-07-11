package com.example.roufy235.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

class SplashScreen : AppCompatActivity() {


    var timeOut = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val prefs = getSharedPreferences("new", 0)

        val new = prefs.getBoolean("new", false)

        if (new){
            val mainActivity = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }else{
            prefs.edit().putBoolean("new", true).apply()
            Handler().postDelayed({
                val mainActivity = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(mainActivity)
                finish()
            }, timeOut.toLong())
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus &&  android.os.Build.VERSION.SDK_INT >= 23){
            var flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            flags = if (android.os.Build.VERSION.SDK_INT < 24) flags else flags or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.decorView.systemUiVisibility = flags
        }
        super.onWindowFocusChanged(hasFocus)
    }
}
