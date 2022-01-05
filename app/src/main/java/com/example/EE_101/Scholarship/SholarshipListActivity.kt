package com.example.EE_101.Scholarship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.EE_101.Financial_Planner.Budget_Planner
import com.example.EE_101.MainActivity
import com.example.EE_101.Notes.NotesListActivity
import com.example.EE_101.R
import com.example.EE_101.To_do_list.To_do_activities.Todo_list_activity
import com.example.EE_101.ViewWebsite
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_budget_planner.*
import kotlinx.android.synthetic.main.activity_sholarship_list.*
import kotlinx.android.synthetic.main.activity_sholarship_list.intentNotes
import kotlinx.android.synthetic.main.activity_sholarship_list.intentTDL

class SholarshipListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var SRRecyclerView : RecyclerView
    private lateinit var SRArrayList : ArrayList<Scholarship>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sholarship_list)

        SRRecyclerView= findViewById(R.id.ScholarshipList)
        SRRecyclerView.layoutManager = LinearLayoutManager(this)
        SRRecyclerView.setHasFixedSize(true)

        SRArrayList = arrayListOf<Scholarship>()
        getScholarshipData()

        backbtn.setOnClickListener {
            var b=Intent(this, MainActivity::class.java)
            startActivity(b)
        }
        addbtn.setOnClickListener {
            var a=Intent(this, ADDScholarship::class.java)
            startActivity(a)
        }

        intentNotes.setOnClickListener {
            val c= Intent(this, NotesListActivity::class.java)
            startActivity(c)
        }
        intentFA.setOnClickListener {
            val d= Intent(this,Budget_Planner::class.java)
            startActivity(d)
        }
        intentTDL.setOnClickListener {
            val e=Intent(this, Todo_list_activity::class.java)
            startActivity(e)
        }

        homebtn.setOnClickListener {
            val f = Intent(this, MainActivity::class.java)
            startActivity(f)
        }
    }
    fun searchWeb(SR : String){
        var i = Intent(this, ViewWebsite::class.java)
        i.putExtra("url",SR)
        startActivity(i)
    }

    private fun getScholarshipData() {
        dbref = FirebaseDatabase.getInstance().getReference("Scholarship")


        dbref.addValueEventListener(object:ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(ScholarshipSnapshot in snapshot.children){

                        val Scholarship= ScholarshipSnapshot.getValue(Scholarship::class.java)
                        SRArrayList.add(Scholarship!!)
                    }
                    var SRadapter= ScholarshipAdapter(SRArrayList)
                    SRRecyclerView.adapter = SRadapter

                    SRadapter.setOnItemClickListener(object:
                        ScholarshipAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            searchWeb(SRArrayList[position].URL.toString())

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