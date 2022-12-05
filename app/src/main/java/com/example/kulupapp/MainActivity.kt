package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.awaitAll
import okhttp3.internal.wait

class MainActivity : AppCompatActivity() {
    //var admin : String? =  ""
    var cubsnames : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_panel)

        //Firebase setting
        var authL = FirebaseAuth.getInstance()
        var databaseClub = FirebaseDatabase.getInstance()
        var databaseReferenceClub = databaseClub?.reference!!.child("club")

        //Clubs names set
        databaseReferenceClub.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        cubsnames += userSnapshot.key.toString()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //swich activity
        val sw1 = findViewById<Switch>(R.id.switch2)
        sw1.setOnClickListener {
            val intentRg = Intent(this, RegisterActivity::class.java)
            startActivity(intentRg)
        }

        //login buttons
        val btnID = findViewById<Button>(R.id.btn_login)//button id area
        val stdnumberID = findViewById<EditText>(R.id.et_stdNumber)
        val passwordID = findViewById<EditText>(R.id.et_password)

        val stdnumber = stdnumberID.text//text value set area
        val pas = passwordID.text

        btnID.setOnClickListener {//Button logic
            if (stdnumber.isEmpty()) {
                stdnumberID.error = "Öğrenci numarasını giriniz"
            } else if (pas.isEmpty()) {
                passwordID.error = "Şifrenizi giriniz"
            } else {
                authL.signInWithEmailAndPassword("${stdnumber.toString()}@kocaeli.edu.tr", pas.toString())
                    .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var authR = FirebaseAuth.getInstance()
                        var database = FirebaseDatabase.getInstance()
                        var databaseReference = database?.reference!!.child("users")
                        val curentUser = authR.currentUser
                        val curentUserDb = databaseReference?.child(curentUser?.uid!!)

                        curentUserDb!!.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                 var admin = snapshot.child("admin").value.toString()
                                if ((cubsnames?.indexOf(admin) != -1) && (admin.isNotEmpty())){
                                    val intentAdm = Intent(baseContext, ClupAdministrationActivitiy::class.java)
                                    startActivity(intentAdm)
                                    finish()
                                }
                                else{
                                    val intentSt = Intent(baseContext, StartActivity::class.java)
                                    startActivity(intentSt)
                                    finish()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }


            val database = Firebase.database
            val myRef = database.getReference("message")

            myRef.setValue("Not Problem!")
        }
    }
}
