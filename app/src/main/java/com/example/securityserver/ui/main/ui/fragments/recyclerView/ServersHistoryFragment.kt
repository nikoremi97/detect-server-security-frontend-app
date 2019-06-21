package com.example.securityserver.ui.main.ui.fragments.recyclerView

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.securityserver.R
import com.example.securityserver.data.Domain
import com.example.securityserver.services.ServiceBaseSingleton
import org.json.JSONObject


class ServersHistoryFragment : Fragment(), Response.Listener<JSONObject>, Response.ErrorListener {

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

	private var listener: OnFragmentInteractionListener? = null

	// domainList will show our Domains in a RecyclerView
	var domainList: RecyclerView? = null
	// a progressbar will be shown when the request is being doing
	var progressBar: ProgressBar? = null

	// arrayDomain will contain the domains to put into domainList
	var arrayDomain = arrayOf<Domain>()

	var jsonObjectRequest: JsonObjectRequest? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		this.onAttach()
		arguments?.let { }
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		println("inside onCreateView >>>")
//
//		// create a server example
//		val detailServer1 = DetailsServer("10.0.0.0", "A+", "US", "Amazon, Inc")
//		val arrayServers: Array<DetailsServer> = arrayOf(detailServer1)
//		val domain1 = Domain(
//			"truora.com", arrayServers, false, "A+", "A",
//			"https://uploads-ssl.webflow.com/5b559a554de48fbcb01fd277/5b97f0b5a5d9b667283cc41a_icon256.png",
//			"Truora", false
//		)
//		val arrayDomain2: Array<Domain> = arrayOf(domain1)

		val viewServerHistory: View = inflater.inflate(R.layout.fragment_servers_history, container, false)

		val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
		val recyclerAdapter = RecyclerAdapterAServersHistory(arrayDomain)

		domainList = viewServerHistory.findViewById(R.id.recycler_view_list_domains)
		domainList?.layoutManager = layoutManager
		domainList?.adapter = recyclerAdapter

		println(domainList)

		// INITIALIZE THE REQUEST TO GET SERVERS FROM DB
		getDomainsInDB()

		return viewServerHistory
	}

	// TODO: Rename method, update argument and hook method into UI event
	fun onButtonPressed(uri: Uri) {
		listener?.onFragmentInteraction(uri)
	}


	interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		fun onFragmentInteraction(uri: Uri)
	}


	private fun getDomainsInDB(){
//		progressBar = ProgressBar(context)
//		progressBar?.visibility = VISIBLE

		// create the url for the getServers endpoint
		val urlEndpoint = getString(R.string.backend_url) + getString(R.string.get_servers_endpoint)
		// jsonObjectRequest will connect with volley to make the requests
		val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,urlEndpoint,null,this,this)
		// add request to our ServiceBaseSingleton request queue
		ServiceBaseSingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
	}

	override fun onResponse(response: JSONObject) {

		try{

		val storedDomains = ServiceBaseSingleton.getInstance(context!!).parseStoredDomains(response.toString())

		arrayDomain = arrayDomain.plus(storedDomains!!.items)
		val recyclerAdapter = RecyclerAdapterAServersHistory(arrayDomain)
		domainList?.adapter = recyclerAdapter
		} catch (e: Exception){
			println(e)
			Toast.makeText(context,getString(R.string.cant_connect_server), Toast.LENGTH_LONG).show()
		}

	}

	override fun onErrorResponse(error: VolleyError) {
		println(error)
		Toast.makeText(context,getString(R.string.cant_connect_server), Toast.LENGTH_LONG).show()

	}
}
