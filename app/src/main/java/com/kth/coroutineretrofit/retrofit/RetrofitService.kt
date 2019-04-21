package com.kth.coroutineretrofit.retrofit

import com.kth.coroutineretrofit.model.Film
import com.kth.coroutineretrofit.model.People
import com.kth.coroutineretrofit.model.StarWars
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Url
import retrofit2.http.GET

interface RetrofitService {

    @GET("people/1")
    fun getPeople(@Query("format") format:String): Deferred<People>

    @GET("films/")
    fun getFilmData(@Url url: String, @Query("format") format: String): Deferred<Film>
}