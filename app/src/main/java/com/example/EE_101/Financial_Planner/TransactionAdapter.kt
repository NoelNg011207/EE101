package com.example.EE_101.Financial_Planner

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.EE_101.R

class TransactionAdapter(private var transactions:List<Transaction>):
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class TransactionHolder(view: View):RecyclerView.ViewHolder(view){
        val Label: TextView =view.findViewById(R.id.TVlabel)
        val amount: TextView =view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = transactions[position]
        val context = holder.amount.context

        if(transaction.amount >= 0){
            holder.amount.text = "+ RM%.2f".format(transaction.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else {
            holder.amount.text = "- RM%.2f".format(Math.abs(transaction.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
           holder.Label.text=transaction.label


        holder.itemView.setOnClickListener {
            val intent=Intent(context, DetailedActivity::class.java)
            intent.putExtra("transaction",transaction)
            context.startActivity(intent)
        }

        }
        override fun getItemCount(): Int {
            return transactions.size
    }

    fun setData(transactions: List<Transaction>){
        this.transactions=transactions
        notifyDataSetChanged()
    }
}
