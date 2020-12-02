package com.dripr.dripr.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dripr.dripr.fragments.main.DiscoverFragment
import com.dripr.dripr.fragments.main.EventsFragment
import com.dripr.dripr.fragments.main.MapFragment
import com.dripr.dripr.fragments.main.MessagesFragment

class ViewPagerMainAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return DiscoverFragment()
            1 -> return MapFragment()
            2 -> return EventsFragment()
            3 -> return MessagesFragment()
        }
        return DiscoverFragment()
    }

    override fun getCount(): Int {
        return 4
    }
}