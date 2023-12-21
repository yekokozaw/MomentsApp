package com.padc.moments.network.storage

interface PresenceInterface {

    fun checkState(
        onSuccess :(Boolean) -> Unit,
        onFailure : (String) -> Unit
    )

    fun addUserId(
        state : Boolean
    )
}