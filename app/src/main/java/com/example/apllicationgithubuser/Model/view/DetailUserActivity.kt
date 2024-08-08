package com.example.apllicationgithubuser.Model.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apllicationgithubuser.Adapter.SectionsPagerAdapter
import com.example.apllicationgithubuser.Model.ViewModel.DetailViewModel
import com.example.apllicationgithubuser.Model.ViewModel.FavoriteViewModel
import com.example.apllicationgithubuser.Model.factory.FavoriteViewModelFactory
import com.example.apllicationgithubuser.Model.favoriteAdd.FavoriteAddViewModel
import com.example.apllicationgithubuser.R
import com.example.apllicationgithubuser.data.local.entity.UserEntity
import com.example.apllicationgithubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val KEY_USER = "user"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }


    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel

    private lateinit var favoriteAddViewModel: FavoriteAddViewModel

    private var isFavorite: Boolean = false
    private var user: List<UserEntity>? = null
    private lateinit var avatarUrlNew: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAddViewModel = obtainViewModel(this@DetailUserActivity)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username.isNullOrEmpty()) {
            finish()
            return
        }

        val modelFav = obtainViewModelFav(this@DetailUserActivity)
        val fabButton: FloatingActionButton = findViewById(R.id.fab_favorite)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val avatarUrl = intent.getStringExtra("avatar_url")

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if (username != null) {
            detailViewModel.setDetailUsers(username)
        }

        showLoading(true)
        detailViewModel.setDetailUsers(username.toString())
        detailViewModel.detailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    lengkap.text = it.name
                    rUsername.text = it.login
                    rFollowers.text = "${it.followers} Followers"
                    rFollowing.text = "${it.following} Following"
                    avatarUrlNew = it.avatarUrl
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(profilegambar)
                    showLoading(false)
                }
            }
        }
        binding.collapToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }


        modelFav.getFav(username).observe(this@DetailUserActivity) { list ->
            user = list
            isFavorite = !list.isNullOrEmpty()
            setFabIcon(fabButton)
        }

        fabButton.setOnClickListener {
            val favUser = UserEntity()
            favUser.username = username
            favUser.avatarUrl = avatarUrlNew

            if (user.isNullOrEmpty() || user!![0].username != username) {
                favoriteAddViewModel.insert(favUser)
                showMessage(getString(R.string.add))
            } else {
                favoriteAddViewModel.delete(favUser)
                showMessage(getString(R.string.delete))
            }
            modelFav.getFav(username)
        }

    }

    private fun setFabIcon(fabButton: FloatingActionButton) {
        val drawable: Drawable = if (isFavorite) {
            // If user is favorite, set filled heart icon
            ContextCompat.getDrawable(this, R.drawable.favorite)!!
        } else {
            // If user is not favorite, set border heart icon
            ContextCompat.getDrawable(this, R.drawable.favorite_border)!!
        }
        fabButton.setImageDrawable(drawable)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[FavoriteAddViewModel::class.java]
    }
    private fun obtainViewModelFav(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }
}