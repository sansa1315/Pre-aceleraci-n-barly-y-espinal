package com.alkemy.challengue

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.alkemy.challengue.databinding.ActivityActorBinding
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActorBinding
    val api_key = "aa3f6f0aa999cf507167f479a1f98cd0"
    val Base_url = "https://api.themoviedb.org/3/"
    val languague = "es-MX"
    lateinit var actor_id : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actor_id = intent.getStringExtra("actor_id").toString()


        getActorDetail()



    }

    private fun getActorDetail() {

        var logg = HttpLoggingInterceptor()
        logg.setLevel(HttpLoggingInterceptor.Level.BODY)
        var okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logg)
            .build()
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base_url)
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getPersonDetail(actor_id, api_key, languague)
        retrofitData.enqueue(object : Callback<ActorDetail> {
            override fun onResponse(
                call: Call<ActorDetail>,
                response: Response<ActorDetail>?
            ) {
                if (response?.isSuccessful!!) {
                    val responseBody = response?.body()!!
                    val imageBasePath = "https://image.tmdb.org/t/p/w300"
                    binding.detailActorName.text = responseBody.name
                    binding.detailActorBiography.text = responseBody.biography ?: "Proximamente"
                    Picasso.get().load(imageBasePath + responseBody.profile_path).into(binding.detailActorImage)
                    binding.detailActorBirthday.text = "Fecha de nacimiento "+ responseBody.birthday
                    binding.detailActorDeathday.text = "Fecha de muerte " + responseBody.deathday ?: "Aun vive"
                    binding.detailActorPlaceOfBirth.text = "Lugar de nacimiento " + responseBody.place_of_birth


                } else {
                    Toast.makeText(applicationContext, "response en failure", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ActorDetail>?, t: Throwable) {
                Toast.makeText(getApplicationContext(), "Failure: " + t.message, Toast.LENGTH_SHORT)
                    .show()
                Log.d("Main Activity", "onFailure: " + t.message)
            }
        })

    }
}