package com.aeryz.seimbanginapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aeryz.seimbanginapp.data.local.database.dao.TransactionDao
import com.aeryz.seimbanginapp.data.local.database.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionDatabase? = null
        private const val DB_NAME = "Transaction.db"

        @JvmStatic
        fun getDatabase(context: Context): TransactionDatabase {
            if (INSTANCE == null) {
                synchronized(TransactionDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as TransactionDatabase
        }
    }
}
