package com.newfortech.app

import android.app.Application
import com.newfortech.app.data.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewForTechApp : Application() {
    
    companion object {
        lateinit var apiService: ApiService
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        initializeRetrofit()
    }
    
    private fun initializeRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        apiService = retrofit.create(ApiService::class.java)
    }
}
