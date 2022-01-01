package com.example.EE_101.Notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.EE_101.databinding.ActivityAddnotesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addnotes.*

class ADDnotes : AppCompatActivity() {
    private lateinit var binding: ActivityAddnotesBinding
    private lateinit var databasenote: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddnotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

       Back2NotesButton.setOnClickListener {
           var a=Intent(this, NotesListActivity::class.java)
           startActivity(a)
       }

        binding.ADDnotesButton.setOnClickListener {
            var subtitle= binding.etnotesTitle.text.toString()
            var URLnote = binding.etnotesURL.text.toString()

            databasenote= FirebaseDatabase.getInstance().getReference("Notes")
            var Subject= NotesSub(subtitle,URLnote)
            databasenote.child(subtitle).setValue(Subject).addOnSuccessListener {


                binding.etnotesTitle.text.clear()
                binding.etnotesURL.text.clear()


                Toast.makeText(this,"Subject added", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Subject failed to edit", Toast.LENGTH_SHORT).show()
            }

            var d= Intent(this, NotesListActivity::class.java)
            startActivity(d)
            }
        binding.DLTnotesButton.setOnClickListener {

            var dltsubjectTitle = binding.etdeletenoteTitle.text.toString()
            if (dltsubjectTitle.isNotEmpty())
                deleteData(dltsubjectTitle)
            else
                Toast.makeText(this,"Please enter the subject to delete", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteData(subjectTitle: String){
        databasenote= FirebaseDatabase.getInstance().getReference("Notes")
        databasenote.child(subjectTitle).removeValue().addOnSuccessListener {

            binding.etdeletenoteTitle.text.clear()
            Toast.makeText(this,"Sucessfully delete", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Please type in the subject name again ", Toast.LENGTH_SHORT).show()
        }
    }

}