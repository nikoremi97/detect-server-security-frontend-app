package com.example.securityserver.ui.main.ui.fragments.recyclerView

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.securityserver.R
import com.example.securityserver.data.Domain
import com.example.securityserver.services.ServiceBaseSingleton
import com.example.securityserver.utils.Utils
import org.json.JSONObject

class ServersHistoryFragment : Fragment(), Response.Listener<JSONObject>, Response.ErrorListener,
	SwipeRefreshLayout.OnRefreshListener {

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment ServersHistoryFragment.
		 */
		@JvmStatic
		fun newInstance() =
			ServersHistoryFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	/**
	 * Components in ServerHistoryFragment
	 */
	// domainList will show our Domains in a RecyclerView
	private var domainList: RecyclerView? = null
	// Swipe refresh layout that allows to refresh servers pulling down the screen
	private var swipeRefreshDomains: SwipeRefreshLayout? = null
	// ProgressBar dialog
	private var progressCircularBar: Dialog? = null
	// arrayDomain will contain the domains to put into domainList
	private var arrayDomain = arrayOf<Domain>()
	private var emptyDomainView: View? = null
	// recycler adapter to insert Domains in DB
	private val recyclerAdapter = RecyclerAdapterAServersHistory(arrayDomain)

	//region Functions
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { }
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		// inflate view for fragment
		val viewServerHistory: View = inflater.inflate(R.layout.fragment_servers_history, container, false)

		progressCircularBar = Dialog(context!!, android.R.style.Theme_Translucent_NoTitleBar)

		// creates layout manager and pass an empty array to the recycler adapter
		val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

		// config recycler view values
		domainList = viewServerHistory.findViewById(R.id.recycler_view_list_domains)
		domainList?.layoutManager = layoutManager
		domainList?.adapter = recyclerAdapter

		// empty view in recycler view
		emptyDomainView= viewServerHistory.findViewById(R.id.empty_domain_layout)

		checkEmpty()

		// associate swipeRefreshDomains to its layout item
		swipeRefreshDomains = viewServerHistory.findViewById(R.id.swipe_refresh_domains)
		swipeRefreshDomains?.setOnRefreshListener(this)
		val color2 = resources.getColor(R.color.white)
		val color1 = resources.getColor(R.color.colorPrimary)
		val color3 = resources.getColor(R.color.colorPrimaryDark)
		swipeRefreshDomains?.setColorSchemeColors(color1, color2, color3)

		// INITIALIZE THE REQUEST TO GET SERVERS FROM DB
		getDomainsInDB()

		return viewServerHistory
	}

	private fun getDomainsInDB() {
		// starts to show progressCircularBar
		progressCircularBar?.setContentView(R.layout.progress_bar_layout)
		progressCircularBar?.setCancelable(false)
		progressCircularBar?.show()
		try {
			// create the url for the getServers endpoint
//		val urlEndpoint = getString(R.string.backend_url) + getString(R.string.get_domain_endpoint)
			val urlEndpoint = "http://192.168.1.57:3000/detectServerSecurity/api/v1/getServers"
			println(urlEndpoint)
			// jsonObjectRequest will connect with volley to make the requests
			val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, urlEndpoint, null, this, this)
			// add request to our ServiceBaseSingleton request queue
			ServiceBaseSingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
		} catch (e: Exception){
			progressCircularBar?.dismiss()
			if (activity != null) {
				Toast.makeText(activity, getString(R.string.cant_connect_server), Toast.LENGTH_LONG).show()
			}
		}
	}


	// onRefresh is called every time fragment is swiped down
	override fun onRefresh() {
		getDomainsInDB()
		Handler().postDelayed({ swipeRefreshDomains?.isRefreshing = false }, 5000)
	}

	private fun checkEmpty() {
		println("checkEmpty >>>")
		emptyDomainView?.visibility = if (recyclerAdapter.itemCount == 0) View.VISIBLE else View.GONE
		domainList?.visibility = if (recyclerAdapter.itemCount == 0) View.GONE else View.VISIBLE
	}
	//endregion

	//region Response from Volley Request region
	override fun onResponse(response: JSONObject) {
		progressCircularBar?.dismiss()
		try {
			val storedDomains = Utils.parseStoredDomains(response.toString())
			arrayDomain = storedDomains!!.items

//			val recyclerAdapter = RecyclerAdapterAServersHistory(arrayDomain)
			recyclerAdapter.domains = arrayDomain
			domainList?.adapter = recyclerAdapter

			checkEmpty()
		} catch (e: Exception) {
			println(e)
			if (activity != null) {
				Toast.makeText(activity, getString(R.string.cant_connect_server), Toast.LENGTH_LONG).show()
			}
		}
	}

	override fun onErrorResponse(error: VolleyError) {
		progressCircularBar?.dismiss()
		println(error)
		println(error.stackTrace)
		println(error.networkResponse)
		if (activity != null) {
			Toast.makeText(activity, getString(R.string.cant_connect_server), Toast.LENGTH_LONG).show()
		}
	}
	//endregion

}
