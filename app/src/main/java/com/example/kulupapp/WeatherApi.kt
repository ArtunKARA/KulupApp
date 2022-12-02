package com.example.kulupapp

import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
class WeatherApi : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_api)

        val buttonX=findViewById<Button>(R.id.button)
        //buttonX.setOnClickListener{



            var URL="https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=Kabaoğlu";
            if(URL.isNotEmpty()){
                val client = OkHttpClient();
                val request = Request.Builder()
                    .url(URL)
                    .get()
                    .addHeader("X-RapidAPI-Key",
                        "524e12a2c3msh3e2cad74c6aa2f6p1d44a0jsn99379ad8c944")
                    .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com")
                    .build();
                client.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }
                    override fun onResponse(call: Call, response:
                    Response
                    ) {
                        response.use {
                            if (!response.isSuccessful){
                                val x:String="something didn't load"
                            }
                            else{
                                var body=response.body?.string();
                                val gson=GsonBuilder().create();
                                val hava=gson.fromJson(body,getHava::class.java)


                                val textHava=findViewById<TextView>(R.id.textView8);
                                textHava.text=hava.temp.toString();




                            }
                        }
                    }
                })
            }
        //}
    }

//Api'nin veri çekmesi için gerekli class sistemi
class getHava(val cloud_pct:Int,val temp:Double,val feel_like:Int,val humidity:Int,val min_temp:Int,val max_tempt:Int,
val wind_speed:Double,val wind_degrees:Int,val sunrise:Int,val sunset:Int)}