package com.example.locationapp.Models

data class Location(
    val id: Int,
    val naam: String,
    val longitude: Double,
    val latitude: Double,
    val data: String
) {
    companion object {
        fun distanceBetween(latitude: Double, longitude: Double, latitude1: Double, longitude1: Double, results: FloatArray) {

        }
    }
}