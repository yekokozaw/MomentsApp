package com.padc.moments.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padc.moments.data.vos.TokenVO
import com.padc.moments.persistence.daos.MomentDao

@Database(entities = [TokenVO::class], version = 1, exportSchema = false)
abstract class MomentDatabase : RoomDatabase() {

    abstract fun getDao() : MomentDao

    companion object{
        private var roomDB:MomentDatabase? =  null
        private const val dbName = "MomentDB"

        fun getDbInstance(context: Context) : MomentDatabase? {
            when(roomDB){
                null -> {
                    roomDB = Room.databaseBuilder(context,MomentDatabase::class.java, dbName)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return roomDB
        }

    }
}