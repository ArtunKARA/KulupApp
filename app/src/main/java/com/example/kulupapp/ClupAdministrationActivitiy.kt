package com.example.kulupapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.kulupapp.ClupAdministrationActivitiy

class ClupAdministrationActivitiy : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<user>
    lateinit var curentClub : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clup_administration_activitiy_panel)

        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("users")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(curentUser?.uid!!)


        //get club member info recyclerview
        curentUserDb?.addValueEventListener(object : ValueEventListener {//set witch admin club
            override fun onDataChange(snapshot: DataSnapshot) {
                curentClub = snapshot.child("admin").value.toString()
                setContentView(R.layout.clup_administration_activitiy_panel)

                userRecyclerView = findViewById(R.id.userList)

                userRecyclerView.layoutManager = LinearLayoutManager(this@ClupAdministrationActivitiy)
                userRecyclerView.setHasFixedSize(true)

                userArrayList = arrayListOf<user>()
                getUserData(curentClub)

                //Club Info Button
                val btnEdt = findViewById<Button>(R.id.btn_editClup)

                btnEdt.setOnClickListener {
                val intent = Intent(this@ClupAdministrationActivitiy,ClubInfoAdminActivity::class.java)
                startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }

    //Get target user data
    private fun getUserData(curentClub : String) {
        dbref = FirebaseDatabase.getInstance().getReference("users")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user =userSnapshot.getValue(user::class.java)
                        if (user!!.club == curentClub){//Selector for club member
                            userArrayList.add(user!!)
                        }
                    }

                    var count = findViewById<TextView>(R.id.textView9)//set member count
                    count.text = userArrayList.size.toString()
                    userRecyclerView.adapter = CustomAdapter(userArrayList,this@ClupAdministrationActivitiy)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}