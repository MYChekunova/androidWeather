package com.example.lab1

data class WeatherData(
    val city: String,
    val time: String,
    val currentTemp: String,
    val windDir: String,
    val humidity: Int,
    val icon: String
)