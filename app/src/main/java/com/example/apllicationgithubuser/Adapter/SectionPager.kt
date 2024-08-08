package com.example.apllicationgithubuser.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apllicationgithubuser.Model.view.FollowersFragment
import com.example.apllicationgithubuser.Model.view.FollowingFragment

class SectionPager(fragmentActivity: AppCompatActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2

    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}