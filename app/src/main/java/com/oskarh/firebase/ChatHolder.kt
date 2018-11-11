package com.oskarh.firebase

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameField: TextView = itemView.findViewById(R.id.name_text)
    private val message: TextView = itemView.findViewById(R.id.message_text)

    fun bind(chat: ChatMessage) {
        nameField.text = chat.name
        message.text = chat.message
    }
}