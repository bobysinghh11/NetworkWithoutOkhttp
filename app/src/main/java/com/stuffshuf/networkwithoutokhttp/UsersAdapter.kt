package com.stuffshuf.networkwithoutokhttp

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.users_list.view.*

class UsersAdapter(val user:ArrayList<Users>):RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val li=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=li.inflate(R.layout.users_list, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val users=user[position]
        holder.itemView.tvuser.text=users.login
        holder.itemView.tvId.text=users.id.toString()

    }

    class UsersViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    }
