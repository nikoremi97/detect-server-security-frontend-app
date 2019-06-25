package com.example.securityserver.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
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


	/**
	 * To change ssl indicator image
	 */
	@JvmStatic
	fun updateSSLGradeIndicator(sslGrade: String?, context: Context?): Drawable? {
		println("inside updateSSLGradeIndicator >>>")
		var newBackground: Drawable? = null

		try {
			val sslGradeOptions = arrayOf("A+", "A", "B", "C", "D", "E", "F")
			newBackground =
				when (sslGrade) {
					sslGradeOptions[0], sslGradeOptions[1] ->
						ContextCompat.getDrawable(context!!, R.drawable.ssl_indicator_a)
					sslGradeOptions[2] -> ContextCompat.getDrawable(context!!, R.drawable.ssl_indicator_b)
					sslGradeOptions[3] -> ContextCompat.getDrawable(context!!, R.drawable.ssl_indicator_c)
					sslGradeOptions[4] -> ContextCompat.getDrawable(context!!, R.drawable.ssl_indicator_d)
					sslGradeOptions[5] -> ContextCompat.getDrawable(context!!, R.drawable.ssl_indicator_e)
					else -> ContextCompat.getDrawable(context!!, R.drawable.ssl_indicator_f)
				}
			println("newBackground >>>")
			println(newBackground)


		} catch (e: Exception) {
			println(e)
		}
		return newBackground
	}
}