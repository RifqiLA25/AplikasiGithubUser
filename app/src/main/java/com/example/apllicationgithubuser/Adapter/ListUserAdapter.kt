package com.example.apllicationgithubuser.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apllicationgithubuser.data.remote.response.user
import com.example.apllicationgithubuser.databinding.ItemRowUserBinding
import com.example.apllicationgithubuser.utils.UIHelper.Companion.setImageGlide


class ListUserAdapter :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val ListUser = ArrayList<user>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: user) {
            binding.root.setOnClickListener {
                Log.d("ListUserAdapter", "  ${user.login}")
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                imgListUser.setImageGlide(itemView.context, user.avatarUrl)
                jUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(ListUser [position])
    }

    override fun getItemCount(): Int = ListUser.size

    fun updateData(newList: ArrayList<user>) {
        Log.d("ListUserAdapter", "updateData called")
        ListUser.clear()
        ListUser.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: user)
    }
}