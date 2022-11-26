package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_panel)
        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("profile")

        //switch activity
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
        val repas  = repaswordID.text

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
            else if (pas.toString() != repas.toString()){
                Toast.makeText(this, "Şifre ve şifre terarı aynı olmalıdır", Toast.LENGTH_SHORT).show()
                repaswordID.error = "Şifre ve şifre terarı aynı olmalıdır"
            }
            else if(pas.length<6){
                passwordID.error = "Şifreniz en az 6 haneden oluşmalıdır olmalıdır"
            }
            else{
                authR.createUserWithEmailAndPassword(("${stdnumber.toString()}@kocaeli.edu.tr"),pas.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        val curentUser = authR.currentUser
                        val curentUserDb = databaseReference?.child(curentUser?.uid!!)
                        curentUserDb?.child("name")?.setValue(name.toString())
                        curentUserDb?.child("email")?.setValue(email.toString())
                        curentUserDb?.child("faculty")?.setValue("")
                        curentUserDb?.child("department")?.setValue("")
                        curentUserDb?.child("telephone")?.setValue("")

                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Giriş Yapınız", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Kayıt esnasında hata oluştu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}