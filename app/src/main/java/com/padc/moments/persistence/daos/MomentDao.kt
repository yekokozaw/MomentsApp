package com.padc.moments.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.moments.data.vos.TokenVO

@Dao
interface MomentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToken(token : TokenVO)

    @Query("SELECT * FROM token_table")
    fun getToken() : TokenVO
}