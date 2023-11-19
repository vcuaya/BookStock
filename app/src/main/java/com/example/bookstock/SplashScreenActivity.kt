package com.hivebraintech.bookstock

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //Variables para animacion de logo
        val ivwLogoSplashScreen:ImageView = findViewById(R.id.ivwLogoSplashScreen)
        val fadeInOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein_fadeout)
        ivwLogoSplashScreen.startAnimation(fadeInOutAnimation)
        fadeInOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                // La animación ha terminado, oculta el logo y luego inicia la actividad MainActivity
                ivwLogoSplashScreen.alpha = 0f
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}
