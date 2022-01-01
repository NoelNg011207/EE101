package com.example.EE_101

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.EE_101.Notes.NotesListActivity
import com.example.EE_101.Scholarship.SholarshipListActivity
import com.example.EE_101.To_do_list.To_do_activities.Todo_list_activity
import com.example.EE_101.Financial_Planner.Budget_Planner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScholarshioNewsImgButton.setOnClickListener {
            val i = Intent(this, SholarshipListActivity::class.java)
            startActivity(i)
            finish()
        }
        NotesImgButton.setOnClickListener {
            val j = Intent(this, NotesListActivity::class.java)
            startActivity(j)
            finish()
        }
        ToDoListImgButton.setOnClickListener {
            val k=Intent(this, Todo_list_activity::class.java)
            startActivity(k)
            finish()
        }
        FinancialAidImgButton.setOnClickListener {
            val l=Intent(this, Budget_Planner::class.java)
            startActivity(l)
            finish()
        }
    }
}