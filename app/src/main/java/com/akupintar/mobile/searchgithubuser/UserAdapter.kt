package com.akupintar.mobile.searchgithubuser

import User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter(private val context: Context, private val items: List<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val name = view.findViewById<TextView>(R.id.tvUserName)
        private val image = view.findViewById<ImageView>(R.id.ivAvatar)

        fun bindItem(items: User) {
            name.text = items.login
            items.avatar_url?.let { Picasso.get().load(it).fit().into(image) }
        }
    }
}