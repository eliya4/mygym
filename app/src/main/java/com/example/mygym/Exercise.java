package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Exercise extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch exercises from the API
        fetchExercises();
    }

    private void fetchExercises() {
        // Logging Interceptor for debugging (Optional)
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttpClient to add headers
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("X-RapidAPI-Key", "YOUR_RAPIDAPI_KEY") // Replace with your key
                            .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/") // Base URL of the API
                .client(client) // Add the client with headers
                .addConverterFactory(GsonConverterFactory.create()) // Convert JSON to Java objects
                .build();

        // Create an instance of the API interface
        ExerciseApi api = retrofit.create(ExerciseApi.class);

        // API call to fetch exercises
        Call<List<ExerciseModel>> call = api.getExercises();

        // Execute the API call
        call.enqueue(new Callback<List<ExerciseModel>>() {
            @Override
            public void onResponse(Call<List<ExerciseModel>> call, Response<List<ExerciseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Initialize the adapter with the fetched data
                    adapter = new ExerciseAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("Exercise", "API call failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ExerciseModel>> call, Throwable t) {
                Log.e("Exercise", "API call failed: ", t);
            }
        });
    }
}
