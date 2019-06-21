package com.example.securityserver.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.securityserver.data.StoredDomains
import com.google.gson.Gson

class ServiceBaseSingleton constructor(context: Context) {
	companion object {
		@Volatile
		private var INSTANCE: ServiceBaseSingleton? = null
		fun getInstance(context: Context) =
			INSTANCE ?: synchronized(this) {
				INSTANCE ?: ServiceBaseSingleton(context).also {
					INSTANCE = it
				}
			}
	}
	private val requestQueue: RequestQueue by lazy {
		// applicationContext is key, it keeps you from leaking the
		// Activity or BroadcastReceiver if someone passes one in.
		Volley.newRequestQueue(context.applicationContext)
	}
	fun <T> addToRequestQueue(req: Request<T>) {
		requestQueue.add(req)
	}

	fun parseStoredDomains(content: String?): StoredDomains? {
		if (content != null) {
			try {
				return Gson().fromJson(content, StoredDomains::class.java)

			} catch (e: Exception) {
				print("error gson >>>")
				print(e)
			}
		}
		return null
	}
}