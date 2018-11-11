package com.oskarh.firebase

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ChatMessage(var name: String? = null,
                       var message: String? = null,
                       var uid: String? = null,
                       @ServerTimestamp
                       var timestamp: Date? = null)