package com.example.kulupapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminUserInfoActivity : AppCompatActivity() {
    lateinit var userclb : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_user_info_panel)

        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("users")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(intent.getStringExtra("putkey").toString())
        val adminUserDb = databaseReference?.child(curentUser!!.uid)

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
                userclb = snapshot.child("club")?.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //btn click event
        btnID.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this)

            dialogBuilder.setMessage("Kulanınıcı Kulüpten Kaldırılsınmı?")
                .setCancelable(true)
                .setPositiveButton("Sil", DialogInterface.OnClickListener {
                        dialog, id ->
                        // delete user information this club

                    adminUserDb?.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                             var thiscluplength = snapshot.child("admin").value.toString().length
                             var thiscluplocation = userclb.indexOf(snapshot.child("admin").value.toString())
                             curentUserDb?.child("club")?.setValue(userclb.substring(0,thiscluplocation)+userclb.substring(thiscluplocation+thiscluplength,userclb.length-1))
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                        val intent = Intent(this,ClupAdministrationActivitiy::class.java)
                        Toast.makeText(this, "Kulanınıcı Kulüpten Kaldırılmıştır", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
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