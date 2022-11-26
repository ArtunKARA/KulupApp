package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_panel)

        //Firebase setting
        var authL = FirebaseAuth.getInstance()

        //swich activity
        val sw1 = findViewById<Switch>(R.id.switch2)
        sw1.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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
                authL.signInWithEmailAndPassword(
                    "${stdnumber.toString()}@kocaeli.edu.tr",
                    pas.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, StartActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }


            val database = Firebase.database
            val myRef = database.getReference("message")

            myRef.setValue("Hellodsfdsf!")
        }
    }
}