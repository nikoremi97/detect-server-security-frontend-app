package com.example.securityserver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.securityserver.ui.main.ui.fragments.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

// MainActivity contains ServerAnalyzerFragment and ServersHistoryFragment in a SectionsPagerAdapter
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val sectionsPagerAdapter =
			SectionsPagerAdapter(this, supportFragmentManager)
		val viewPager: ViewPager = findViewById(R.id.view_pager)
		viewPager.adapter = sectionsPagerAdapter
		val tabs: TabLayout = findViewById(R.id.tabLayout)
		tabs.setupWithViewPager(viewPager)

		// change the icon of the tabs
		tabs.getTabAt(0)?.setIcon(R.drawable.lens)
		tabs.getTabAt(1)?.setIcon(R.drawable.clock)

	}
}
