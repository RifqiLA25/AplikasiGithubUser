package com.example.apllicationgithubuser.Model.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apllicationgithubuser.Adapter.ListUserAdapter
import com.example.apllicationgithubuser.Model.ViewModel.FollowingViewModel
import com.example.apllicationgithubuser.Model.view.DetailUserActivity.Companion.EXTRA_USERNAME
import com.example.apllicationgithubuser.data.remote.response.user
import com.example.apllicationgithubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private lateinit var  binding: FragmentFollowingBinding
    private lateinit var listAdapter: ListUserAdapter
    private lateinit var followingviewModel: FollowingViewModel
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        listAdapter = ListUserAdapter()
        listAdapter.notifyDataSetChanged()

        binding.apply {
            pBVItem.setHasFixedSize(true)
            pBVItem.layoutManager = LinearLayoutManager(activity)
            pBVItem.adapter = listAdapter
        }

        listAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: user) {
                val intent = Intent(requireContext(), DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, user.login)
                startActivity(intent)
            }
        })

        showLoading(true)
        followingviewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java
        )
        followingviewModel.getSetFollowing(username)
        followingviewModel.following().observe(viewLifecycleOwner, {
            if (it != null) {
                listAdapter.updateData(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pBVLoading.visibility = View.VISIBLE
        } else {
            binding.pBVLoading.visibility = View.GONE
        }
    }
}