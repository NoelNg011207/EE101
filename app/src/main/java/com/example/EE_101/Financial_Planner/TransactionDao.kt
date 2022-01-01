package com.example.EE_101.Financial_Planner

import androidx.room.*
import com.example.EE_101.Financial_Planner.Transaction

@Dao

interface TransactionDao {
    @Query("SELECT* from transactions")
    fun getAll():List<Transaction>

    @Insert
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Update
    fun update (vararg transaction: Transaction)


}