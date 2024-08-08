package com.example.apllicationgithubuser.Adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.apllicationgithubuser.Model.view.FollowersFragment
import com.example.apllicationgithubuser.Model.view.FollowingFragment
import com.example.apllicationgithubuser.R

class SectionsPagerAdapter(
    private val context: Context,
    private val manager: FragmentManager,
    private val bundle: Bundle
) : FragmentPagerAdapter(manager, BEHAVIOR_SET_USER_VISIBLE_HINT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val fragment = FollowersFragment()
                fragment.arguments = bundle
                return fragment
            }

            1 -> {
                val fragment = FollowingFragment()
                fragment.arguments = bundle
                return fragment
            }

            else -> {
                throw IllegalArgumentException("Invalid position")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }
}