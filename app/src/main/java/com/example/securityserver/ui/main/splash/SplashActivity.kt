package com.example.securityserver.ui.main.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.securityserver.MainActivity

class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val intent = Intent(baseContext, MainActivity::class.java)
		startActivity(intent)
		finish()
		}

}
