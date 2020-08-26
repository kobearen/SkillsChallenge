package com.example.skillschallenge

data class RandomUser(var results: List<Result>,
                      var info: Info)

data class Info (var see: String)

data class Result (var gender: String,
                   var email: String)