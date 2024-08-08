package com.example.apllicationgithubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apllicationgithubuser.R
import com.example.apllicationgithubuser.data.local.entity.UserEntity
import com.example.apllicationgithubuser.databinding.ItemRowUserBinding

class FavoriteAdapter : ListAdapter<UserEntity, FavoriteAdapter.ViewHolder>(CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserEntity) {
            binding.jUsername.text = item.username
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .centerCrop()
                .error(R.drawable.profile)
                .into(binding.imgListUser)

            // Set onClickListener
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(item, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserEntity, position: Int)
    }

    companion object {
        private val CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}