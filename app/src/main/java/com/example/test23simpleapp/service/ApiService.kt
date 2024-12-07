package com.example.test23simpleapp.service

import com.example.test23simpleapp.model.Model
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getData(): Model
}