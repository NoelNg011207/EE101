package com.example.EE_101.Scholarship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.EE_101.databinding.ActivityAddscholarshipBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ADDScholarship : AppCompatActivity() {
    private lateinit var binding: ActivityAddscholarshipBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddscholarshipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnADD.setOnClickListener {
            var SRname = binding.etScholar.text.toString()
            var deadline = binding.etClsosingDate.text.toString()
            var Requirements = binding.etAboutScholar.text.toString()
            var shortSRname= binding.etScholar.text.toString()
            var URL=binding.etURL.text.toString()

            database =FirebaseDatabase.getInstance().getReference("Scholarship")
            var Scholarship= Scholarship(Requirements,SRname,deadline,shortSRname,URL)
            database.child(shortSRname).setValue(Scholarship).addOnSuccessListener {

                binding.etScholar.text.clear()
                binding.etClsosingDate.text.clear()
                binding.etAboutScholar.text.clear()
                binding.etURL.text.clear()

                Toast.makeText(this,"Scholarship added",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Scholarship failed to edit",Toast.LENGTH_SHORT).show()
            }

            var c=Intent(this, SholarshipListActivity::class.java)
            startActivity(c)
        }
    }
}