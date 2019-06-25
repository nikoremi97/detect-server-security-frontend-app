package com.example.securityserver.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class ServiceBaseSingleton constructor(context: Context) {

	companion object {
		@Volatile
		private var INSTANCE: ServiceBaseSingleton? = null

		// to use the same instance every time we use this class
		fun getInstance(context: Context) =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?: ServiceBaseSingleton(context).also {
					INSTANCE = it
				}
			}
	}

	// Create Volley RequestQueue
	private val requestQueue: RequestQueue by lazy {
		// applicationContext is key, it keeps you from leaking the
		// Activity or BroadcastReceiver if someone passes one in.
		Volley.newRequestQueue(context.applicationContext)
	}

	// Add request to Queue
	fun <T> addToRequestQueue(req: Request<T>) {
		requestQueue.add(req)
	}

}