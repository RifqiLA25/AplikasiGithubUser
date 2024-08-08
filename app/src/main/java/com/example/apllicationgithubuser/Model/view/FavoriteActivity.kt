package com.example.apllicationgithubuser.Model.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apllicationgithubuser.Adapter.FavoriteAdapter
import com.example.apllicationgithubuser.Model.ViewModel.FavoriteViewModel
import com.example.apllicationgithubuser.Model.factory.FavoriteViewModelFactory
import com.example.apllicationgithubuser.R
import com.example.apllicationgithubuser.data.local.entity.UserEntity
import com.example.apllicationgithubuser.databinding.ActivityFavoriteBinding
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = FavoriteAdapter()

        binding.itmFavorite.layoutManager = LinearLayoutManager(this)
        binding.itmFavorite.adapter = favoriteAdapter
        binding.itmFavorite.setHasFixedSize(true)

        setupToolbar()

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getFavoriteUser().observe(this) { Fav ->
            if (Fav.isEmpty()) {
                binding.tvMessage.visibility = View.VISIBLE
                binding.itmFavorite.visibility = View.GONE
            } else {
                binding.tvMessage.visibility = View.GONE
                binding.itmFavorite.visibility = View.VISIBLE
                favoriteAdapter.submitList(Fav)
            }
        }

        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserEntity, position: Int) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, user.username)
                intent.putExtra("avatar_url", user.avatarUrl)
                startActivity(intent)
            }
        })

        favoriteViewModel.getFavoriteUser().observe(this) { favoriteUser ->
            if (favoriteUser == null) {
                Snackbar.make(binding.root, "Error loading favorites", Snackbar.LENGTH_SHORT).show()
            } else {
                // Memperbarui RecyclerView dengan daftar pengguna favorit yang diperoleh dari ViewModel
                favoriteAdapter.submitList(favoriteUser)
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.t_favorite)
        }
    }

//    private fun setupRecyclerView() {
//        favoriteViewModel.getFavoriteUser().observe(this) { users ->
//            val items = arrayListOf<UserEntity>()
//            users.map {
//                val item = UserEntity(username = it.username, avatarUrl = it.avatarUrl)
//                items.add(item)
//            }
//            favoriteAdapter.submitList(items)
//        }
//    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}