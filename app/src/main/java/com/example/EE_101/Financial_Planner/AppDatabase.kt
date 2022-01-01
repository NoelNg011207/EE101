package com.example.EE_101.Financial_Planner

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Transaction::class),version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao


}