package com.example.EE_101.Scholarship

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.EE_101.R

class ScholarshipAdapter
    (private val SRlist: ArrayList<Scholarship> )
    : RecyclerView.Adapter<ScholarshipAdapter.SRViewHolder>() {

    private lateinit var onListerner : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){
        onListerner= listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SRViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(
            R.layout.sholarship_item,
            parent,
            false)
        return SRViewHolder(itemView,onListerner)

    }

    override fun onBindViewHolder(holder: SRViewHolder, position: Int) {

        val curItem=SRlist[position]

        holder.SRName.text= curItem.SRname
        holder.deadline.text= curItem.deadline
        holder.Requirements.text=curItem.Requirements
    }

    override fun getItemCount(): Int {
        return SRlist.size
    }

    class SRViewHolder(itemView:View,listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val SRName : TextView = itemView.findViewById(R.id.tvscholarshipname)
        val Requirements : TextView = itemView.findViewById(R.id.tvrequirements)
        val deadline : TextView = itemView.findViewById(R.id.tvdeadline)
        val URL : TextView=itemView.findViewById(R.id.tvURL)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(absoluteAdapterPosition)
            }
        }

    }
}