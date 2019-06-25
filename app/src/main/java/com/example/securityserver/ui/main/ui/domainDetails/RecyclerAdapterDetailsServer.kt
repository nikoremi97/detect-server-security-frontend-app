package com.example.securityserver.ui.main.ui.domainDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.securityserver.R
import com.example.securityserver.data.DetailsServer
import kotlinx.android.synthetic.main.item_server.view.*

class RecyclerAdapterDetailsServer(currentServers: Array<DetailsServer>?) :
	RecyclerView.Adapter<RecyclerAdapterDetailsServer.ViewHolder>() {

	var servers: Array<DetailsServer>? = null
	private var viewHolder: ViewHolder? = null

	init {
		this.servers = currentServers
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val fragmentList = LayoutInflater.from(parent.context).inflate(R.layout.item_server, parent, false)
		viewHolder = ViewHolder(fragmentList)

		return viewHolder!!
	}

	override fun getItemCount(): Int {
		return this.servers?.size!!
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		val server = servers!![position]
		holder.address?.text = server.address
		holder.sslGrade?.text = server.ssl_grade
		holder.country?.text = server.country
		holder.owner?.text = server.owner
	}

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		private val serverView = view
		var address: AppCompatTextView? = null
		var sslGrade: AppCompatTextView? = null
		var country: AppCompatTextView? = null
		var owner: AppCompatTextView? = null

		init {
			this.address = serverView.server_ip_address
			this.sslGrade = serverView.server_ssl_grade
			this.country = serverView.server_country
			this.owner = serverView.server_owner
		}
	}
}

