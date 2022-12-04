package com.example.chappar10.data.api;


import com.example.chappar10.data.api.JokeApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static JokeApi jokeApi;

    public static JokeApi getJokeApi() {
        if (jokeApi == null) {
            jokeApi = new Retrofit.Builder()
                    .baseUrl("https://official-joke-api.appspot.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(JokeApi.class);
        }
        return jokeApi;
    }
}
