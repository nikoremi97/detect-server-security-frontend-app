package com.example.securityserver.data

import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson

object ServiceBase {

	const val urlBase = "http:192.168.1.57:3000/detectServerSecurity/api/v1"

	fun getServers(): StoredDomains? {
		var storedDomains: StoredDomains? = null

		val getEndpoint = "/getServers"
		val url = urlBase + getEndpoint

		try {
			url.httpGet().responseString { _, response, result ->
				println("response.statusCode >>>")
				println(response.statusCode)
				when (response.statusCode) {
					200 -> {
						print(result)
						val (domainsInDb, resultError) = result

						if (resultError == null) {
							storedDomains = deserializeStoredDomains(domainsInDb)
							println(storedDomains)

						} else {
							println(resultError)
						}

					}
					else -> print(response.statusCode)

				}
			}
		} catch (e: Exception) {
			print(e)
		}

		return storedDomains
	}

	fun deserializeStoredDomains(content: String?): StoredDomains? {
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