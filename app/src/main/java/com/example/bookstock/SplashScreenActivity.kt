package com.hivebraintech.bookstock

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

        // Obtener la referencia al ImageView
        val ivwLogoSplashScreen: ImageView = findViewById(R.id.ivwLogoSplashScreen)

        // Cargar la animación de desvanecimiento
        val fadeInOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein_fadeout)

        // Iniciar la animación de desvanecimiento
        ivwLogoSplashScreen.startAnimation(fadeInOutAnimation)

        fadeInOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {

                // Establecer la opacidad del ImageView a 0
                ivwLogoSplashScreen.alpha = 0f

                // Ir a MainActivity
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)

                // Finalizar SplashScreenActivity
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}
