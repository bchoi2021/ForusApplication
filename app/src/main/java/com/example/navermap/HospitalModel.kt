package com.example.navermap

data class HospitalModel(
    val id: Int,
    val title: String,
    val address: String,
    val tell: String,
    val lat: Double,
    val lng: Double,
    val imgUrl: String
)
