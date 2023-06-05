package com.example.navermap

import retrofit2.Call
import retrofit2.http.GET

interface HospitalService {
    @GET("/v3/735cfdd7-7bb9-4130-bd1b-2d1ec30f2c41")
    fun getHospitalList(): Call<HospitalDto>
}