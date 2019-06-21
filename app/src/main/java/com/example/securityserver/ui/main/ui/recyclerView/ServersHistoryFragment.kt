package com.example.securityserver.ui.main.ui.recyclerView

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.securityserver.R
import com.example.securityserver.data.DetailsServer
import com.example.securityserver.data.Domain

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ServersHistoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ServersHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ServersHistoryFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var listener: OnFragmentInteractionListener? = null
	var domainList: RecyclerView? = null
	val arrayServers = emptyArray<DetailsServer>()
	val arrayDomain = emptyArray<Domain>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { }

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		// create a server example
		val detailServer1 = DetailsServer("10.0.0.0", "A+", "US", "Amazon, Inc")
		arrayServers.plus(detailServer1)
		val domain1 = Domain(
			"truora.com", arrayServers, false, "A+", "A",
			"https://uploads-ssl.webflow.com/5b559a554de48fbcb01fd277/5b97f0b5a5d9b667283cc41a_icon256.png",
			"Truora", false
		)
		arrayDomain.plus(domain1)
		var layoutManager: RecyclerView.LayoutManager? = null
		var recyclerAdapter: RecyclerAdapterAServersHistory? = null

		var viewServerHistory: View = inflater.inflate(R.layout.fragment_servers_history, container, false)

		layoutManager = LinearLayoutManager(context)
		recyclerAdapter = RecyclerAdapterAServersHistory(arrayDomain)

		domainList = viewServerHistory.findViewById(R.id.recycler_view_list_domains)
		domainList?.layoutManager = layoutManager
		domainList?.adapter = recyclerAdapter
//	}


		return viewServerHistory

	}

	// TODO: Rename method, update argument and hook method into UI event
	fun onButtonPressed(uri: Uri) {
		listener?.onFragmentInteraction(uri)
	}


	override fun onDetach() {
		super.onDetach()
		listener = null
	}


//
//	override fun onAttach(context: Context) {
//		super.onAttach(context)
//		if (context is OnFragmentInteractionListener) {
//			listener = context
//		} else {
//			throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//		}
//	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 *
	 *
	 * See the Android Training lesson [Communicating with Other Fragments]
	 * (http://developer.android.com/training/basics/fragments/communicating.html)
	 * for more information.
	 */
	interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		fun onFragmentInteraction(uri: Uri)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment BlankFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance() =
			ServersHistoryFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}
}


//	fun createStoredDomains() = runBlocking {
//		println("here in createStoredDomains>>>")
// to be enable to interact with UI inside coroutine
//		storedDomains = ServiceBase.getServers()
//		println("out async >>>")
//		println("storedDomains main activity >>>")
//		println(storedDomains)
//		if (storedDomains != null) {
//			println("storedDomains != null")
