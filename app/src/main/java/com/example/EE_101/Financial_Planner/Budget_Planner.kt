package com.example.EE_101.Financial_Planner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.EE_101.MainActivity
import com.example.EE_101.Notes.NotesListActivity
import com.example.EE_101.R
import com.example.EE_101.Scholarship.SholarshipListActivity
import com.example.EE_101.To_do_list.To_do_activities.Todo_list_activity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_budget_planner.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Budget_Planner : AppCompatActivity() {
    private lateinit var deletedTransaction: Transaction
    private lateinit var transactions:List<Transaction>
    private lateinit var oldTransactions:List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var LinearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_planner)

        transactions = arrayListOf()

        transactionAdapter= TransactionAdapter(transactions)
        LinearLayoutManager = LinearLayoutManager(this)

        db=Room.databaseBuilder(this, AppDatabase::class.java,
            "transactions").build()

        recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager
        }

        val swipeHelper=ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(recyclerview)



        addBtn.setOnClickListener {
            val intent  = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
        Homebtn.setOnClickListener {
            val Intent  = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }

        FAbackbtn.setOnClickListener {
            val a=Intent(this,MainActivity::class.java)
            startActivity(a)
        }

        intentNotes.setOnClickListener {
            val b= Intent(this,NotesListActivity::class.java)
            startActivity(b)
        }
        intentScholarship.setOnClickListener {
            val c= Intent(this,SholarshipListActivity::class.java)
            startActivity(c)
        }
        intentTDL.setOnClickListener {
            val d=Intent(this,Todo_list_activity::class.java)
            startActivity(d)
        }
    }
    private fun fetchAll(){
        GlobalScope.launch {
            transactions=db.transactionDao().getAll()

            runOnUiThread {
                updateDashboard()
                transactionAdapter.setData(transactions)
            }
        }
    }


    //Enable swipe to remove
    val itemTouchHelper=object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT)
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            deleteTransaction(transactions[viewHolder.adapterPosition])
        }

    }

        private fun updateDashboard() {
            val totalAmount: Double = transactions.map { it.amount }.sum()
            val budgetAmount: Double = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
            val expenseAmount: Double = totalAmount - budgetAmount

            balance.text = "RM %.2f".format(totalAmount)
            budget.text = "RM %.2f".format(budgetAmount)
            expense.text = "RM %.2f".format(expenseAmount)
        }
    private fun undoDelete()
    {
        GlobalScope.launch {
            db.transactionDao().insertAll(deletedTransaction)

            transactions=oldTransactions

            runOnUiThread {
                transactionAdapter.setData(transactions)
                updateDashboard()
            }
        }
    }
    private fun showSnackbar()
    {
        val view=findViewById<View>(R.id.coordinator)
        val snackbar=Snackbar.make(view,"Transaction deleted",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this,R.color.red))
            .setTextColor(ContextCompat.getColor(this,R.color.white))
            .show()
    }
    private fun deleteTransaction(transaction: Transaction)
    {
        deletedTransaction = transaction
        oldTransactions=transactions

        GlobalScope.launch {
            db.transactionDao().delete(transaction)

            transactions=transactions.filter{it.id!=transaction.id}
            runOnUiThread {
                updateDashboard()
                showSnackbar()
                transactionAdapter.setData(transactions)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}