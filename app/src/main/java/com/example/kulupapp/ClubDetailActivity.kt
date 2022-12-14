package com.example.kulupapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ClubDetailActivity : AppCompatActivity() {
    var clups : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_detail_panel)

        //firebase user set
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("users")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(curentUser?.uid!!)

        //get club info firebase
        var databaseClub = FirebaseDatabase.getInstance()
        var databaseReferenceClub = databaseClub?.reference!!.child("club")
        val curentClubDb = databaseReferenceClub?.child(intent.getStringExtra("putkey").toString())


        curentClubDb?.addValueEventListener(object : ValueEventListener {//club info setting area
        override fun onDataChange(snapshot: DataSnapshot) {
            var clpname = findViewById<TextView>(R.id.tv_clpname2)
            val longInfo = findViewById<TextView>(R.id.tv_longinfo)
            val clbpitch = findViewById<ImageView>(R.id.imageView)

            Picasso.with(this@ClubDetailActivity).load(snapshot.child("photoClub").value.toString())
                .placeholder(R.drawable.hehe)
                .into(clbpitch)
            clpname.text = snapshot.child("name").value.toString()
            longInfo.text = snapshot.child("longInfo").value.toString()
        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        curentUserDb?.addValueEventListener(object : ValueEventListener {//club info setting area
        override fun onDataChange(snapshot: DataSnapshot) {
            clups = snapshot.child("club").value.toString()
            val btn = findViewById<Button>(R.id.btn_registerclp)
            val have = clups!!.indexOf(findViewById<TextView>(R.id.tv_clpname2).text.toString())
            if (have == -1){
                btn.setOnClickListener {
                    if(clups == null){
                        curentUserDb?.child("club")?.setValue(findViewById<TextView>(R.id.tv_clpname2).text.toString())
                    }
                    else{
                        curentUserDb?.child("club")?.setValue(clups+findViewById<TextView>(R.id.tv_clpname2).text.toString())
                    }
                }
            }
            else{
                btn.text = "Kay??tl??s??n??z"
            }
        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}