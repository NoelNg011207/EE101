package com.example.EE_101.Notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.EE_101.MainActivity
import com.example.EE_101.R
import com.example.EE_101.ViewWebsite
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_notes_list.*

class NotesListActivity : AppCompatActivity() {
    private lateinit var dbref1 : DatabaseReference
    private lateinit var NRecyclerView : RecyclerView
    private lateinit var NArrayList : ArrayList<NotesSub>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        NRecyclerView= findViewById(R.id.rvSubTitleItems)
        NRecyclerView.layoutManager = LinearLayoutManager(this)
        NRecyclerView.setHasFixedSize(true)

        NArrayList = arrayListOf<NotesSub>()
        getNotesData()

        var c=Intent(this, ADDnotes::class.java)
        addsubjectBtn.setOnClickListener {
            startActivity(c)
            finish()
        }
        dltTitleButton.setOnClickListener {
            startActivity(c)
            finish()
        }

        notebackbtn.setOnClickListener {
            var d=Intent(this, MainActivity::class.java)
            startActivity(d)
            finish()
        }
    }
    fun searchWeb(Notes : String){
        var i = Intent(this, ViewWebsite::class.java)
        i.putExtra("url",Notes)
        startActivity(i)
    }

    private fun getNotesData() {
        dbref1 = FirebaseDatabase.getInstance().getReference("Notes")

        dbref1.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(NotesSnapshot in snapshot.children){

                        val Notes= NotesSnapshot.getValue(NotesSub::class.java)
                        NArrayList.add(Notes!!)
                    }
                    var Nadapter= NoteSubAdapter(NArrayList)
                    NRecyclerView.adapter = Nadapter

                    Nadapter.setOnItemClickListener(object: NoteSubAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            searchWeb(NArrayList[position].URL.toString())

                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}
