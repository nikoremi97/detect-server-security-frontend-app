package com.example.securityserver.ui.main.ui.domainDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.securityserver.R
import com.example.securityserver.data.DetailsServer
import com.example.securityserver.data.Domain
import com.example.securityserver.utils.Utils

class DomainDetailsActivity : AppCompatActivity() {

	//region Activity components
	// view components
	private var domainLogo: AppCompatImageView? = null
	private var sslGradeIndicator: AppCompatImageView? = null
	private var domainName: AppCompatTextView? = null
	private var domainTitle: AppCompatTextView? = null
	private var domainSSLGrade: AppCompatTextView? = null
	private var domainPreviousSSLGrade: AppCompatTextView? = null
	private var domainServersChanged: AppCompatTextView? = null
	private var domainIsDown: AppCompatTextView? = null
	private var serversList: RecyclerView? = null

	// Adapter for server item in recycler view
	private var arrayServers = arrayOf<DetailsServer>()
	private val recyclerAdapter = RecyclerAdapterDetailsServer(arrayServers)

	// objects
	private var domain: Domain? = null

	//endregion

	//region Functions
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_domain_description)
		// change appbar name
		supportActionBar?.title = getString(R.string.domain_details)
		// to create the go back to MainActivity button
		supportActionBar?.setHomeButtonEnabled(true)
		// set views
		domainLogo = findViewById(R.id.domain_description_view_logo)
		sslGradeIndicator = findViewById(R.id.server_domain_ssl_indicator)
		domainName = findViewById(R.id.domain_description_view_domain)
		domainTitle = findViewById(R.id.domain_description_view_domain_title)
		domainSSLGrade = findViewById(R.id.server_domain_ssl_grade)
		domainPreviousSSLGrade = findViewById(R.id.server_domain_previous_ssl_grade)
		domainServersChanged = findViewById(R.id.server_domain_servers_changed)
		domainIsDown = findViewById(R.id.server_domain_is_down)
		// config recycler view
		val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
		// config recycler view values
		serversList = findViewById(R.id.recycler_view_servers_list)
		serversList?.layoutManager = layoutManager

		// get Domain data
		val bundleObject = intent.extras
		domain = bundleObject?.getSerializable("domain") as Domain

		recyclerAdapter.servers = domain?.servers
		serversList?.adapter = recyclerAdapter

		// change views components values
		setViews(domain)
	}

	// setViews change UI values depending on a given Domain object
	private fun setViews(domain: Domain?) {
		Utils.updateImageViewWithUrl(domain?.logo, domainLogo)
		domainName?.text = domain?.domain
		domainTitle?.text = domain?.title
		domainSSLGrade?.text = domain?.ssl_grade
		domainPreviousSSLGrade?.text = domain?.previous_ssl_grade
		domainServersChanged?.text = domain?.servers_changed.toString()
		domainIsDown?.text = domain?.is_down.toString()
		sslGradeIndicator?.background = Utils.updateSSLGradeIndicator(domain?.ssl_grade, this)
	}

	//endregion

}
