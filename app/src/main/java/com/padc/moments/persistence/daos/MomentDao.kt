package com.padc.moments.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.padc.moments.data.vos.TokenVO
import retrofit2.http.DELETE

@Dao
interface MomentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToken(token : TokenVO)

    @Transaction
    @Query("DELETE from token_table")
    fun deleteToken()

    @Query("SELECT * FROM token_table")
    fun getToken() : TokenVO
}