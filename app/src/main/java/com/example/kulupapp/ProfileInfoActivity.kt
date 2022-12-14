package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profileinfo_panel)

        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("users")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(curentUser?.uid!!)

        //Components settings
        val  btnID  = findViewById<Button>(R.id.btn_delMemeber) //Components id set
        val  nameID = findViewById<TextView>(R.id.tv_name2)
        val  stdnumberID = findViewById<TextView>(R.id.tv_stdnumber1)
        val  facultyID = findViewById<TextView>(R.id.tv_faculty1)
        val  departmentID = findViewById<TextView>(R.id.tv_department1)
        val  phonenumberID = findViewById<TextView>(R.id.tv_phonenumber1)
        val  emailId = findViewById<TextView>(R.id.tv_email1)

        //data getting area
        curentUserDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nameID.text = snapshot.child("name").value.toString()
                stdnumberID.text = curentUser?.email?.subSequence(0,9)
                facultyID.text = snapshot.child("faculty").value.toString()
                departmentID.text = snapshot.child("department").value.toString()
                phonenumberID.text = snapshot.child("telephone").value.toString()
                emailId.text = snapshot.child("email").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //button setting area

        btnID.setOnClickListener {
            val intent = Intent(this,ProfileUpdateActivity::class.java)
            startActivity(intent)
        }

    }
}