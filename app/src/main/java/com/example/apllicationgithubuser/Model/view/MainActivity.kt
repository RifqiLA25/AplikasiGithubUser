package com.example.apllicationgithubuser.Model.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apllicationgithubuser.Adapter.ListUserAdapter
import com.example.apllicationgithubuser.Model.ViewModel.MainViewModel
import com.example.apllicationgithubuser.R
import com.example.apllicationgithubuser.data.remote.response.user
import com.example.apllicationgithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var listAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = ListUserAdapter()
        listAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        listAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: user) {
                val intent = Intent(
                    this@MainActivity,
                    DetailUserActivity::class.java
                ).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            resultUser.layoutManager = LinearLayoutManager(this@MainActivity)
            resultUser.setHasFixedSize(true)
            resultUser.adapter = listAdapter


            btnSend.setOnClickListener {
                setupUI()
            }

            edReview.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    setupUI()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
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

        viewModel.Users().observe(this) {
            if (it != null) {
                listAdapter.updateData(it)
                showLoading(false)
            }
        }

    }

    private fun setupUI() {
        binding.apply {
            val query = edReview.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.searchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progresLoading.visibility = View.VISIBLE
        } else {
            binding.progresLoading.visibility = View.GONE
        }
    }

}