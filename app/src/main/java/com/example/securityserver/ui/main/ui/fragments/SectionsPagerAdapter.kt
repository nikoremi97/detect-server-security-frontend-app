package com.example.securityserver.ui.main.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.securityserver.ui.main.ui.fragments.recyclerView.ServersHistoryFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

	override fun getItem(position: Int): Fragment {
		println("position >>>")
		println(position)
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class below).
		return when (position) {
			0 -> {
				ServerAnalyzerFragment.newInstance()
			}
			1 -> {
				ServersHistoryFragment.newInstance()
			}
			else -> {
				ServerAnalyzerFragment.newInstance()
			}
		}
	}


	override fun getCount(): Int {
		// Show 2 total pages.
		return 2
	}
}