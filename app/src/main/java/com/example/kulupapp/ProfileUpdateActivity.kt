package com.example.kulupapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profileupdate_panel)

        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("profile")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(curentUser?.uid!!)

        //Components settings
        val nameID = findViewById<EditText>(R.id.et_nameupdate)
        val stdnumberID = findViewById<EditText>(R.id.et_nameupdate2)
        val facultyID = findViewById<EditText>(R.id.et_nameupdate3)
        val departmentID = findViewById<EditText>(R.id.et_nameupdate4)
        val phonenumberID = findViewById<EditText>(R.id.et_nameupdate5)
        val emailID = findViewById<EditText>(R.id.et_nameupdate6)
        val btnCID = findViewById<Button>(R.id.btn_cancel1)
        val btnSID = findViewById<Button>(R.id.btn_save1)
        val btnDelID = findViewById<TextView>(R.id.tv_delaccaunt)

        //current value setting
        curentUserDb?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                nameID.setText(snapshot.child("name").value.toString())
                stdnumberID.setText(curentUser?.email?.subSequence(0,9))
                facultyID.setText(snapshot.child("faculty").value.toString())
                departmentID.setText(snapshot.child("department").value.toString())
                phonenumberID.setText(snapshot.child("telephone").value.toString())
                emailID.setText(snapshot.child("email").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //buttons settings area

        btnCID.setOnClickListener {
            val intent = Intent(this , ProfileInfoActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnSID.setOnClickListener {
            val name = nameID.text //get value in to the page
            val stdnumber = stdnumberID.text
            val faculty = facultyID.text
            val department = departmentID.text
            val phonenumber = phonenumberID.text
            val email = emailID.text

            curentUserDb?.child("name")?.setValue(name.toString()) //set value send the database
            curentUserDb?.child("email")?.setValue(email.toString())
            curentUserDb?.child("faculty")?.setValue(faculty.toString())
            curentUserDb?.child("department")?.setValue(department.toString())
            curentUserDb?.child("telephone")?.setValue(phonenumber.toString())

            val intent = Intent(this, ProfileInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        btnDelID.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Hesabınız silinsin mi?")
                .setCancelable(true)
                .setPositiveButton("Sil", DialogInterface.OnClickListener {
                        dialog, id ->
                    curentUser?.delete()?.addOnCompleteListener { //delete user authentication
                        // delete user information
                        val intent = Intent(this,MainActivity::class.java)
                        authR.signOut()
                        Toast.makeText(this, "Hesabınız silinmiştir", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    }
                })
                .setNegativeButton("İPTAL", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("Sil")
            alert.show()

        }

    }
}