package com.example.EE_101.Scholarship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.EE_101.MainActivity
import com.example.EE_101.R
import com.example.EE_101.ViewWebsite
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sholarship_list.*

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