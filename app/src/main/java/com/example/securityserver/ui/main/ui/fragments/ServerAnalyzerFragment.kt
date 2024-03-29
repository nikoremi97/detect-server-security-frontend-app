package com.example.securityserver.ui.main.ui.fragments

import android.app.Dialog
import android.content.Intent
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
import com.example.securityserver.ui.main.ui.domainDetails.DomainDetailsActivity
import com.example.securityserver.utils.Utils
import com.google.android.material.button.MaterialButton
import org.json.JSONObject

// ServerAnalyzerFragment is the fragment that analyze a new domain
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

	// Attributes in fragments
	private var userInput: EditText? = null
	private var domainInputText: String = ""
	private var scanButton: MaterialButton? = null

	// ProgressBar dialog
	private var progressCircularBar: Dialog? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { }
	}

	// set attributes values when the view is created
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

	// check if the domain is a valid input and calls scanDomain function
	override fun onClick(v: View) {
		val userInputText = userInput?.text.toString()
		val isValidInput = userInputText.isValidDomain()
		if (isValidInput) {
			domainInputText = userInputText
			scanDomain(userInputText)
		}
	}

	// this function creates a new JsonObjectRequest to be queued in VolleyRequestQueue at ServiceBaseSingleton
	private fun scanDomain(domain: String) {
		// starts to show progressCircularBar
		progressCircularBar?.setContentView(R.layout.progress_bar_layout)
		progressCircularBar?.setCancelable(false)
		progressCircularBar?.show()

		try {
			// creates the json form with "domain" field and the given domain
			val jsonBody = JSONObject()
			println("domain >>>")
			println(domain)
			jsonBody.put("domain", domain)

			// this is the endpoint in backend for a new domain
			val urlEndpoint = getString(R.string.backend_url) + getString(R.string.new_domain_endpoint)
			// jsonObjectRequest will connect with volley to make the requests
			val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlEndpoint, jsonBody, this, this)
			// add request to our ServiceBaseSingleton request queue
			ServiceBaseSingleton.getInstance(context!!).addToRequestQueue(jsonObjectRequest)

		} catch (e: Exception) {
			progressCircularBar?.dismiss()
			if (activity != null) {
				Toast.makeText(activity, getString(R.string.cant_scan_server), Toast.LENGTH_LONG).show()
			}
		}
	}

	//region Response from Volley Request region
	// Handles an OK response from Volley
	override fun onResponse(response: JSONObject) {
		progressCircularBar?.dismiss()

		try {

			// parse the response and creates a newDomain object
			val newDomain = Utils.parseDomain(response.toString())

			// start a new DomainDetailsActivity with the newDomain
			val intent = Intent(activity, DomainDetailsActivity::class.java)
			val bundleObject = Bundle()
			bundleObject.putSerializable("domain", newDomain)
			intent.putExtras(bundleObject)
			startActivity(intent)


		} catch (e: Exception) {
			println(e)
			if (activity != null) {
				Toast.makeText(activity, getString(R.string.cant_scan_server), Toast.LENGTH_LONG).show()
			}
		}
	}

	// Handles an ErrorResponse from Volley
	override fun onErrorResponse(error: VolleyError) {
		progressCircularBar?.dismiss()
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

}
