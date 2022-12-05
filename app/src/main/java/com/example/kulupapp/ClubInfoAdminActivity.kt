package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ClubInfoAdminActivity : AppCompatActivity() {
    var curentClub : String? = null
    lateinit var curentClubDb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_info_admin_panel)

        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("users")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(curentUser?.uid!!)

        //admin property setting
        curentUserDb?.addValueEventListener(object : ValueEventListener {//set witch admin club
        override fun onDataChange(snapshot: DataSnapshot) {
            curentClub = snapshot.child("admin").value.toString()

            //get club info
            var databaseClub = FirebaseDatabase.getInstance()
            var databaseReferenceClub = databaseClub?.reference!!.child("club")
            val curentClubDb = databaseReferenceClub?.child(curentClub!!)

            //club name set
            val clubname = findViewById<TextView>(R.id.tv_adminclubname)
            clubname.text = curentClub

            //club info get area
            curentClubDb?.addValueEventListener(object : ValueEventListener {//club info setting area
            override fun onDataChange(snapshot: DataSnapshot) {
                val shortInfoPanel = findViewById<EditText>(R.id.et_shortInfoClub1)
                val longInfoPanel = findViewById<EditText>(R.id.et_longInfoClub)
                shortInfoPanel.setText(snapshot.child("shortInfo").value.toString())
                longInfoPanel.setText(snapshot.child("longInfo").value.toString())
            }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            //short info counter
            val shortInfoPanel = findViewById<EditText>(R.id.et_shortInfoClub1)
            shortInfoPanel.addTextChangedListener {
                if (shortInfoPanel.text.length <= 280){
                    val shortInfoCounter = findViewById<TextView>(R.id.tv_shortInfoCounter)
                    shortInfoCounter.text = shortInfoPanel.text.length.toString() +"/280"
                }
                else{
                    shortInfoPanel.error = "En fazla 280 karakter olabilir"
                    shortInfoPanel.setText(shortInfoPanel.text.toString().subSequence(0,280))
                }
            }

            //club info set area
            val btnSV = findViewById<Button>(R.id.btn_infoSave)
            btnSV.setOnClickListener {
                val longInfoPanel = findViewById<EditText>(R.id.et_longInfoClub)
                val urlId = findViewById<EditText>(R.id.et_url)
                if (shortInfoPanel.text.length <= 280){
                    curentClubDb?.child("longInfo")?.setValue(longInfoPanel.text.toString())
                    curentClubDb?.child("shortInfo")?.setValue(shortInfoPanel.text.toString())
                    curentClubDb?.child("photoClub")?.setValue(urlId.text.toString())
                    Toast.makeText(this@ClubInfoAdminActivity, "Kayıt başarılı", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@ClubInfoAdminActivity,ClupAdministrationActivitiy::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    shortInfoPanel.error = "En fazla 280 karakter olabilir"
                }
            }

        }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}