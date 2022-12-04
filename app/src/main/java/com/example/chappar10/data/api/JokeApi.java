package com.example.chappar10.data.api;

import com.example.chappar10.data.api.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeApi {
    @GET("/random_joke")
    Call<Joke> getJoke();
}
