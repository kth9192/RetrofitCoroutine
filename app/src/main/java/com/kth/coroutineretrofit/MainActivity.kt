package com.kth.coroutineretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import com.kth.coroutineretrofit.databinding.ActivityMainBinding
import com.kth.coroutineretrofit.retrofit.OkHttp3RetrofitManager
import com.kth.coroutineretrofit.retrofit.RetrofitService
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var activityMainBinding: ActivityMainBinding
    private val myScope = GlobalScope
    private var isLoading: ObservableBoolean = ObservableBoolean(false)

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.isLoading = isLoading

        start_routine.setOnClickListener {
            isLoading.set(true)

            myScope.launch {
                testRestAPi()
            }.let { isLoading.set(!it.isCompleted) }
        }
    }

    private suspend fun testRestAPi() {
        val retrofitService: RetrofitService = OkHttp3RetrofitManager.retrofit.create(RetrofitService::class.java)

        try {
            val result = retrofitService.getPeople("json").await().let {
                retrofitService.getFilmData("json").await()
            }

            Logger.d("결과값 ${result.title}")

            content.text = result.title

        } catch (e: Throwable) {
            Log.e(TAG, e.printStackTrace().toString())
        }

    }
}
