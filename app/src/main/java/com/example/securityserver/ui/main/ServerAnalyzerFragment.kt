package com.example.securityserver.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.securityserver.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ServerAnalyzerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ServerAnalyzerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ServerAnalyzerFragment: Fragment() {
	// TODO: Rename and change types of parameters
	private var listener: OnFragmentInteractionListener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_analyzer, container, false)
	}

	// TODO: Rename method, update argument and hook method into UI event
	fun onButtonPressed(uri: Uri) {
		listener?.onFragmentInteraction(uri)
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

	override fun onDetach() {
		super.onDetach()
		listener = null
	}

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
			ServerAnalyzerFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}
}
