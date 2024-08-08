package com.example.apllicationgithubuser.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.example.apllicationgithubuser.R
import de.hdodenhof.circleimageview.CircleImageView

class UIHelper {

    companion object {
        /**
         * Extension function to set CircleImageView using Glide
         *
         * @param context   Context
         * @param url   Image URL
         * @return Unit
         */
        fun CircleImageView.setImageGlide(context: Context, url: String) {
            Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.profile)
                .into(this)
        }
    }
}