package com.kth.coroutineretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kth.coroutineretrofit.model.People
import com.kth.mycoroutine.retrofit.OkHttp3RetrofitManager
import com.kth.coroutineretrofit.retrofit.RetrofitService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_routine.setOnClickListener {
            GlobalScope.launch {
                testRestAPi()
            }
        }
    }

    suspend fun testRestAPi() {
        var retrofitService: RetrofitService = OkHttp3RetrofitManager.retrofit.create(RetrofitService::class.java)

        try {
            val result = retrofitService.getPeople("json").await()
            Log.d(TAG, "결과값 ${result.name}")
        }catch (e:Exception){
            Log.e(TAG, e.printStackTrace().toString())
        }
    }
}
