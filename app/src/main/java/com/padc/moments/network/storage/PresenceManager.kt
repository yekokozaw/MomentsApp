package com.padc.moments.network.storage

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PresenceManager (private val userId : String) : PresenceInterface{
    private val presenceRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("presence")
    var watcher = false

    fun setUserOnline() {
        presenceRef.child(userId).setValue(true)
    }

    // Call this method when the user logs out or closes the app
    fun setUserOffline() {
        presenceRef.child(userId).setValue(false)
    }

    override fun checkState(onSuccess: (Boolean) -> Unit, onFailure: (String) -> Unit) {
        presenceRef.child(userId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOnline : Boolean = snapshot.getValue(Boolean::class.java) ?: false

                onSuccess(isOnline)
                Log.d("myTag","This is true")
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }

        })
    }

    override fun addUserId(state: Boolean) {
        presenceRef.child(userId).setValue(state)
    }
}