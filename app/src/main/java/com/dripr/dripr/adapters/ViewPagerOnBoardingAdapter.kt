package com.dripr.dripr.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dripr.dripr.fragments.main.DiscoverFragment
import com.dripr.dripr.fragments.onboarding.OnBoardingFourFragment
import com.dripr.dripr.fragments.onboarding.OnBoardingOneFragment
import com.dripr.dripr.fragments.onboarding.OnBoardingThreeFragment
import com.dripr.dripr.fragments.onboarding.OnBoardingTwoFragment

class ViewPagerOnBoardingAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return OnBoardingOneFragment()
            1 -> return OnBoardingTwoFragment()
            2 -> return OnBoardingThreeFragment()
            3 -> return OnBoardingFourFragment()
        }
        return DiscoverFragment()
    }

    override fun getCount(): Int {
        return 4
    }
}