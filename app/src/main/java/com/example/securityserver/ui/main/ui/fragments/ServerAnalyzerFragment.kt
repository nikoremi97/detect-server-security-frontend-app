package com.example.securityserver.ui.main.ui.fragments

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.securityserver.R
import com.example.securityserver.services.ServiceBaseSingleton
import com.google.android.material.button.MaterialButton
import org.json.JSONObject

class ServerAnalyzerFragment : Fragment(), Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {

	companion object {
		/*
		 * @return A new instance of fragment ServerAnalyzerFragment.
		 */
		@JvmStatic
		fun newInstance() =
			ServerAnalyzerFragment().apply {
				arguments = Bundle().apply {
				}
			}
	}

	private var listener: OnFragmentInteractionListener? = null

	// Components in fragments
	private var userInput: EditText? = null
	private var scanButton: MaterialButton? = null

	// ProgressBar dialog
	private var progressCircularBar: Dialog? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { }
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		// inflate view and set scanButton and userInput values
		val scanAnalyzerView = inflater.inflate(R.layout.fragment_analyzer, container, false)
		scanButton = scanAnalyzerView.findViewById(R.id.scan_domain_button)
		scanButton?.setOnClickListener(this)

		progressCircularBar = Dialog(context!!, android.R.style.Theme_Translucent_NoTitleBar)

		// get and validate user input
		userInput = scanAnalyzerView.findViewById(R.id.domain_input_edit_text)
		userInput?.validate(getString(R.string.invalid_user_input)) { inputText -> inputText.isValidDomain() }
		// return fragment view
		return scanAnalyzerView
	}

	override fun onClick(v: View) {
		val userInputText = userInput?.text.toString()
		val isValidInput = userInputText.isValidDomain()
		if (isValidInput) {
			println("userInputText >>>>>")
			println(userInputText)
			scanDomain(userInputText)
		}
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

	private fun scanDomain(domain: String) {
		// starts to show progressCircularBar
		progressCircularBar?.setContentView(R.layout.progress_bar_layout)
		progressCircularBar?.setCancelable(false)
		progressCircularBar?.show()

		// creates the json form with "domain" field and the given domain
		val jsonBody = JSONObject()
		jsonBody.put("domain", domain)

		// this is the endpoint in backend for a new domain
		val urlEndpoint = getString(R.string.backend_url) + getString(R.string.new_domain_endpoint)

		// jsonObjectRequest will connect with volley to make the requests
		val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlEndpoint, jsonBody, this, this)
		// add request to our ServiceBaseSingleton request queue
		ServiceBaseSingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)
	}

	//region Response from Volley Request region
	override fun onResponse(response: JSONObject) {
		progressCircularBar?.dismiss()

		println(response.toString())
		try {

			val newDomain = ServiceBaseSingleton.getInstance(context!!).parseDomain(response.toString())

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
		if (activity != null) {
			Toast.makeText(activity, getString(R.string.cant_connect_server), Toast.LENGTH_LONG).show()
		}
	}
	//endregion

	//region User input validation region

	/**
	 * All of the functions below are extensions from its classes
	 */

	// checks user's input in the moment he types something
	private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
		this.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(s: Editable?) {
				afterTextChanged.invoke(s.toString())
			}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
		})
	}

	// validate user input according to a given rule. Marks an error in Domain field if the rule is not satisfied
	private fun EditText.validate(message: String, validator: (String) -> Boolean) {
		this.afterTextChanged {
			this.error = if (validator(it)) null else message
		}
		this.error = if (validator(this.text.toString())) null else message
	}

	// Returns true if the given string is a valid domain name, matching with Patterns.DOMAIN_NAME.
	private fun String.isValidDomain(): Boolean = Patterns.DOMAIN_NAME.matcher(this).matches()
	//endregion


	override fun onDetach() {
		super.onDetach()
		listener = null
	}
}
