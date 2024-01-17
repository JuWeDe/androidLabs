package com.example.messenger411.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger411.Data.Chat
import com.example.messenger411.R


class ChatAdapter(private val chatList: List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        val tvChatName: TextView = itemView.findViewById(R.id.tv_chat_name)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]
        holder.tvChatName.text = chat.name
        holder.tvTime.text = chat.time
        holder.tvMessage.text = chat.message
        holder.ivAvatar.setImageResource(chat.avatar)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}
