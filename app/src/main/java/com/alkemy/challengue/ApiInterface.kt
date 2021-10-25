package com.alkemy.challengue

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

//    @GET("movie/popular?api_key={api_key}&language=es")
    @GET("movie/popular")
    fun getMostPopular(
        @Query("api_key") api_key : String,
        @Query("languague") languaje : String
    ):Call<MostPopularData>


//    @GET("movie/580489?api_key=aa3f6f0aa999cf507167f479a1f98cd0&append_to_response=videos&language=es-MX")
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id : String,
        @Query("api_key") api_key : String,
        @Query("append_to_response") append_to_response : String,
        @Query("language") language : String
    ):Call<MovieDetail>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredit(
        @Path("movie_id") movie_id : String,
        @Query("api_key") api_key : String,
    ): Call<MovieCredits>

    @GET("person/{person_id}")
    fun getPersonDetail(
        @Path("person_id") person_id : String,
        @Query("api_key") api_key : String,
        @Query("language") language : String
    ):Call<ActorDetail>

    @POST("movie/{movie_id}/rating")
    fun postRate(
        @Body value: String,
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ):Call<String>





}