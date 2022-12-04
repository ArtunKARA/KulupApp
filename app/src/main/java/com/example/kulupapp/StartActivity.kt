package com.example.kulupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class StartActivity : AppCompatActivity() {
    var dolar:String? = "Yükleniyor"
    private lateinit var dbref: DatabaseReference
    private lateinit var clupRecyclerView: RecyclerView
    private lateinit var clubArrayList: ArrayList<club>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_panel)

        //Firebase setting
        var authR = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var databaseReference = database?.reference!!.child("users")
        val curentUser = authR.currentUser
        val curentUserDb = databaseReference?.child(curentUser?.uid!!)

        var databaseReferenceClub = database?.reference!!.child("club")

        // user setting button
        val btnusrset = findViewById<Button>(R.id.btn_menu)
        btnusrset.setOnClickListener {
            val intent = Intent(this,ProfileInfoActivity::class.java)
            startActivity(intent)
        }

        //user val set
        var nameuser = findViewById<TextView>(R.id.tv_namemain1)
        curentUserDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nameuser.text = snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //API Progress Area
        var URL1="https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Kabaoğlu";//API Url set
        if(URL1.isNotEmpty()){
            val client = OkHttpClient();
            val request = Request.Builder()
                .url(URL1)
                .get()
                .addHeader("X-RapidAPI-Key",
                    "524e12a2c3msh3e2cad74c6aa2f6p1d44a0jsn99379ad8c944")//API Key
                .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
                .build();
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful){
                            val x:String="something didn't load"
                        }
                        else{
                            var body=response.body?.string();
                            val gson=GsonBuilder().create();
                            val hava=gson.fromJson(body, getWeather::class.java)

                            val textHava=findViewById<TextView>(R.id.tv_weather);
                            textHava.text=hava.temp.toInt().toString()+"C";//Set text


                        }
                    }
                }
            })
        }
        var URL2="https://currency-converter-by-api-ninjas.p.rapidapi.com/v1/convertcurrency?have=USD&want=TRY&amount=1";//API Url set
        if(URL2.isNotEmpty()){
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(URL2)
                .get()
                .addHeader("X-RapidAPI-Key", "524e12a2c3msh3e2cad74c6aa2f6p1d44a0jsn99379ad8c944")
                .addHeader("X-RapidAPI-Host", "currency-converter-by-api-ninjas.p.rapidapi.com")
                .build()

            val dolarText=findViewById<TextView>(R.id.tv_dolar);
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful){
                            val x:String="something didn't load"
                        }
                        else{
                            var body=response.body?.string();
                            val gson=GsonBuilder().create();
                            val money=gson.fromJson(body, getCuren::class.java)

                            val textDolar=findViewById<TextView>(R.id.tv_dolar)
                            textDolar.text = money.new_amount.toString()+"TL";//Set text

                        }
                    }
                }
            })
        }
        clupRecyclerView = findViewById(R.id.clupsview)

        clupRecyclerView.layoutManager = LinearLayoutManager(this@StartActivity)
        clupRecyclerView.setHasFixedSize(true)

        clubArrayList = arrayListOf<club>()
        getUserData()






    }

    //Get target club data
    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("club")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val club =userSnapshot.getValue(club::class.java)
                        clubArrayList.add(club!!)
                    }
                    clupRecyclerView.adapter = ClubAdapter(clubArrayList,this@StartActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    //API Value Class
    class getWeather(val cloud_pct:Int,val temp:Double,val feel_like:Int,val humidity:Int,val min_temp:Int,val max_tempt:Int,
                  val wind_speed:Double,val wind_degrees:Int,val sunrise:Int,val sunset:Int)
    class getCuren(val new_amount:Double, val new_currency:String,val old_currency:String,val old_amount:Int)
}