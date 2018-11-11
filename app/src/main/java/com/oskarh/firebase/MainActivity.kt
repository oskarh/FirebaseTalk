package com.oskarh.firebase

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val chatCollection = FirebaseFirestore.getInstance().collection("chats")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sign_out_button.setOnClickListener {
            signOut()
        }
        send_button.setOnClickListener {
            sendMessage()
        }
        messages_recyclerview.adapter = createAdapter()
    }

    private fun createAdapter(): RecyclerView.Adapter<*>? {
        val chatQuery = chatCollection.whereEqualTo("bookingId", 11).orderBy("timestamp").limit(20)
        val options = FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(chatQuery, ChatMessage::class.java)
                .setLifecycleOwner(this)
                .build()

        return object : FirestoreRecyclerAdapter<ChatMessage, ChatHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
                return ChatHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.message, parent, false))
            }

            override fun onBindViewHolder(holder: ChatHolder, position: Int, model: ChatMessage) {
                holder.bind(model)
            }
        }
    }

    private fun sendMessage() {
        val userId = FirebaseAuth.getInstance()?.currentUser?.uid
        val name = FirebaseAuth.getInstance()?.currentUser?.displayName
        val message = message_edittext.text.toString()
        val chatMessage = ChatMessage(name, message, userId)
        chatCollection.add(chatMessage)
        message_edittext.setText("")
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
