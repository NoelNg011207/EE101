package com.example.EE_101.Notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.EE_101.R

class NoteSubAdapter(
    private val Notelist: ArrayList<NotesSub>

):RecyclerView.Adapter<NoteSubAdapter.noteViewHolder>() {
    private lateinit var onListener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        onListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.notesub_item,
            parent,
            false
        )
        return noteViewHolder(itemView, onListener)
    }


    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val curnoteItem = Notelist[position]
        holder.subtitle.text = curnoteItem.subtitle
    }

    override fun getItemCount(): Int {
        return Notelist.size
    }

    class noteViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val subtitle: TextView=itemView.findViewById(R.id.tvsubjecttitle)
        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }
}


