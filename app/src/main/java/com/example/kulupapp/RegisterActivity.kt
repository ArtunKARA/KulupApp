package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_panel)
        //Firebase setting
        var authR = FirebaseAuth.getInstance()

        //swich activity
        val sw1 = findViewById<Switch>(R.id.switch2)

        sw1.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        //register buttons
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

        btnID.setOnClickListener { //Button logic
            if (stdnumber.isEmpty()){
                stdnumberID.error = "Öğrenci numarasını giriniz"
            }
            else if (name.isEmpty()){
                nameID.error = "İsminizi giriniz giriniz"
            }
            else if (email.isEmpty()){
                emailID.error = "Öğrenci numaranızı giriniz"
            }
            else if (pas.isEmpty()){
                passwordID.error = "Şifreniz giriniz giriniz"
            }
            else if (repas.isEmpty()){
                repaswordID.error = "Şifre tekrarınızı giriniz"
            }
            else if (pas != repas){
                Toast.makeText(this, "Şifre ve şifre terarı aynı olmalıdır", Toast.LENGTH_SHORT).show()
                repaswordID.error = "Şifre ve şifre terarı aynı olmalıdır"
            }
            else{
                authR.createUserWithEmailAndPassword(stdnumber.toString(),pas.toString()).addOnCompleteListener {  }
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
        }

    }
}