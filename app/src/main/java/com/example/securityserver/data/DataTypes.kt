package com.example.securityserver.data

/**
 * StoredDomains Class
 */
data class StoredDomains(val items: Array<Domain>)

/**
 * Domain Class
 */
data class Domain(
	val domain: String,
	var servers: Array<DetailsServer>,
	var servers_changed: Boolean,
	var ssl_grade: String,
	var previous_ssl_grade: String,
	val logo: String,
	val title: String,
	var is_down: Boolean = false
)

/**
 * DetailsServer Class
 */
data class DetailsServer(
	val address: String,
	val ssl_grade: String,
	val country: String,
	val owner: String
)

