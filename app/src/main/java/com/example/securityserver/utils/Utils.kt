package com.example.securityserver.utils

import androidx.appcompat.widget.AppCompatImageView
import com.example.securityserver.R
import com.example.securityserver.data.Domain
import com.example.securityserver.data.StoredDomains
import com.google.gson.Gson
import com.squareup.picasso.Picasso

object Utils {

	// Parse Json object to StoredDomain
	@JvmStatic
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

	// Parse Json object to Domain
	@JvmStatic
	fun parseDomain(content: String?): Domain? {
		if (content != null) {
			try {
				return Gson().fromJson(content, Domain::class.java)

			} catch (e: Exception) {
				print("error gson >>>")
				print(e)
			}
		}
		return null
	}

			/**
			 * To change card icons with domain logos
			 */
	@JvmStatic
	fun updateImageViewWithUrl(url: String?, imageView: AppCompatImageView?) {
		try {
			Picasso.get().load(url).error(R.drawable.security_server_color_primary).resize(70, 70).centerCrop()
				.into(imageView)
		} catch (e: Exception) {
			println(e)
		}
	}
}