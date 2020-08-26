package com.example.skillschallenge


interface Client {
    @GET("api")
    fun getUser(): Observable<RandomUser>
}