package com.example.apllicationgithubuser.Model.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apllicationgithubuser.Adapter.ListUserAdapter
import com.example.apllicationgithubuser.Model.ViewModel.FollowerViewModel
import com.example.apllicationgithubuser.Model.view.DetailUserActivity.Companion.EXTRA_USERNAME
import com.example.apllicationgithubuser.data.remote.response.user
import com.example.apllicationgithubuser.databinding.FragmentFollowersBinding


class FollowersFragment : Fragment() {
    private lateinit var _binding: FragmentFollowersBinding
    private lateinit var listAdapter: ListUserAdapter
    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater,container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME) ?: ""

        listAdapter = ListUserAdapter()
        listAdapter.notifyDataSetChanged()

        _binding.apply {
            pBItem.setHasFixedSize(true)
            pBItem.layoutManager = LinearLayoutManager(activity)
            pBItem.adapter = listAdapter
        }

        listAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: user) {
                val intent = Intent(requireContext(), DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, user.login)
                startActivity(intent)
            }
        })

        showLoading(true)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        followerViewModel.getSetFollowers(username)
        followerViewModel.followers().observe(viewLifecycleOwner, { followers ->
            if (followers != null) {
                Log.d("FollowersFragment", "Followers received: $followers")
                listAdapter.updateData(followers)
                showLoading(false)
            } else {
                Log.e("FollowersFragment", "Failed to receive followers")
                // Tambahan log untuk menunjukkan kesalahan jika diperlukan
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            _binding.pBLoading.visibility = View.VISIBLE
        } else {
            _binding.pBLoading.visibility = View.GONE
        }
    }
}