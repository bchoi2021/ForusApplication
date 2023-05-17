package org.techtown.firebase_test

import retrofit2.Call
import retrofit2.http.GET

interface HospitalService {
    @GET("/v3/2a45a3a3-3ea7-48a9-8210-27d2dc006b16")
    fun getHospitalList(): Call<HospitalDto>
}