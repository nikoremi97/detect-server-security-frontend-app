package com.example.securityserver.ui.main.ui.fragments.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.securityserver.R
import com.example.securityserver.data.Domain
import com.example.securityserver.utils.Utils
import kotlinx.android.synthetic.main.item_domain.view.*

class RecyclerAdapterAServersHistory(
	currentDomains: Array<Domain>?,
	onDomainListenerAdapter: OnClickDomainListener?,
	context: Context?
) :
	RecyclerView.Adapter<RecyclerAdapterAServersHistory.ViewHolder>() {

	// the interface that fragments or activities will use to get the position of the clicked item in recyclerView
	interface OnClickDomainListener {
		fun onDomainClick(position: Int)
	}

	// RecyclerAdapterAServersHistory attributes
	var domains: Array<Domain>? = null
	private var viewHolder: ViewHolder? = null
	private var onDomainClickListener: OnClickDomainListener? = null
	private var contextAdapter: Context? = null

	init {
		this.domains = currentDomains
		this.onDomainClickListener = onDomainListenerAdapter
		this.contextAdapter = context
	}

//	Create a new instance of ViewHolder
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val fragmentList = LayoutInflater.from(parent.context).inflate(R.layout.item_domain, parent, false)
		viewHolder = ViewHolder(fragmentList, onDomainClickListener, contextAdapter)
		return viewHolder!!
	}

	// return the size of domains array
	override fun getItemCount(): Int {
		return this.domains?.size!!
	}

	// set ViewHolder attributes
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val domain = domains!![position]
		print(domain)
		Utils.updateImageViewWithUrl(domain.logo, holder.logo)
		holder.name?.text = domain.domain
		holder.title?.text = domain.title
		holder.sslGrade?.text = domain.ssl_grade
		holder.sslIndicator?.background = Utils.updateSSLGradeIndicator(domain.ssl_grade, contextAdapter)
	}

	// Class ViewHolder will contain our domain main description in recyclerView
	// Implements onClickListener to be able to return its position in adapter
	class ViewHolder(view: View, onClickDomainListener: OnClickDomainListener?, context: Context?) :
		RecyclerView.ViewHolder(view),
		View.OnClickListener {

		private val itemDomainView = view
		var logo: AppCompatImageView? = null
		var sslIndicator: AppCompatImageView? = null
		var name: AppCompatTextView? = null
		var sslGrade: AppCompatTextView? = null
		var title: AppCompatTextView? = null
		var onDomainListener: OnClickDomainListener? = null
		var context: Context? = null

		init {
			this.logo = itemDomainView.material_card_view_logo
			this.context = context
			this.sslIndicator = itemDomainView.material_card_ssl_indicator
			this.name = itemDomainView.material_card_domain
			this.sslGrade = itemDomainView.material_card_domain_ssl
			this.title = itemDomainView.material_card_title
			this.onDomainListener = onClickDomainListener
			itemDomainView.setOnClickListener(this)
		}

		// return the adapter position
		override fun onClick(view: View) {
			onDomainListener?.onDomainClick(adapterPosition)
		}
	}

}
