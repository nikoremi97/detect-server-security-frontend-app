package com.example.securityserver.ui.main.ui.fragments.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.securityserver.R
import com.example.securityserver.data.Domain
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_domain.view.*

class RecyclerAdapterAServersHistory(currentDomains: Array<Domain>?) :
	RecyclerView.Adapter<RecyclerAdapterAServersHistory.ViewHolder>() {

	var domains: Array<Domain>? = null
	private var viewHolder: ViewHolder? = null

	init {
		this.domains = currentDomains
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val fragmentList = LayoutInflater.from(parent.context).inflate(R.layout.item_domain, parent, false)
		viewHolder = ViewHolder(fragmentList)

		return viewHolder!!
	}

	override fun getItemCount(): Int {
		return this.domains?.size!!
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val domain = domains!![position]
		print(domain)
		holder.updateWithUrl(domain.logo)

		holder.name?.text = domain.domain
		holder.title?.text = domain.title
		holder.sslGrade?.text = domain.ssl_grade
	}

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		private val itemDomainView = view

		var logo: AppCompatImageView? = null
		var name: AppCompatTextView? = null
		var sslGrade: AppCompatTextView? = null
		var title: AppCompatTextView? = null

		init {
			this.logo = itemDomainView.material_card_view_logo
			this.name = itemDomainView.material_card_domain
			this.sslGrade = itemDomainView.material_card_domain_ssl
			this.title = itemDomainView.material_card_title
		}

		/**
		 * To change card icons with domain logos
		 */
		fun updateWithUrl(url: String) {
			try {
				Picasso.get().load(url).error(R.drawable.security_server_color_primary).resize(70, 70).centerCrop().into(logo)
			} catch (e: Exception) {
				println(e)
			}
		}
	}

}
