package com.example.securityserver.ui.main.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.securityserver.MainActivity

// SplashActivity is initialize at the moment the app starts
// Its only mission is to give a previous view to the user before MainActivity is created
class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val intent = Intent(baseContext, MainActivity::class.java)
		startActivity(intent)
		finish()
	}

}
