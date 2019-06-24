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
		println("inside recycler view >>>")
		this.domains = currentDomains
		println("domains inside recycler view  >>>")
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		println("inside onCreateViewHolder >>>")
		val fragmentList = LayoutInflater.from(parent.context).inflate(R.layout.item_domain, parent, false)
		viewHolder = ViewHolder(fragmentList)

		return viewHolder!!
	}

	// TODO
	override fun getItemCount(): Int {

		println("inside getItemCount >>>")
		println(this.domains?.size!!)
		return this.domains?.size!!
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		println("inside onBindViewHolder >>>")

		val domain = domains!![position]
		print(domain)
		holder.updateWithUrl(domain.logo)
		holder.name?.text = domain.domain
		holder.title?.text = domain.title
		holder.sslGrade?.text = domain.ssl_grade
	}

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val itemDomainView = view

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
			println("here in updateWithUrl >>>")
			println(url)
			try {
				Picasso.get().load(url).error(R.drawable.server).resize(70, 70).centerCrop().into(logo)
				println("picasso success >>>")
			} catch (e: Exception) {
				println(e)

			}
		}
	}

}
