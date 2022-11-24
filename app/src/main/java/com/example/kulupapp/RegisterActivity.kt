package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_panel)
        //swich activity
        val sw1 = findViewById<Switch>(R.id.switch2)

        sw1.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        //register button
        val btnID = findViewById<Button>(R.id.btn_register)//button id area
        val stdnumberID = findViewById<EditText>(R.id.et_stdNumber)
        val nameID = findViewById<EditText>(R.id.et_name)
        val emailID = findViewById<EditText>(R.id.et_email)
        val passwordID = findViewById<EditText>(R.id.et_password)
        val repaswordID = findViewById<EditText>(R.id.et_repassword)

        val stdnumber = stdnumberID.text //text value set area
        val name = nameID.text
        val email = emailID.text
        val pas = passwordID.text
        val repas = repaswordID.text

        btnID.setOnClickListener {
            if (stdnumber.isEmpty()){
                stdnumberID.shadowColor
            }
        }

    }
}