package com.jbiglion22.koreanhistorynote

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 //       requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_intro)

        var actionBar = supportActionBar
        actionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 3000)

    }
}