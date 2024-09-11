package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private DatabaseReference databaseReference;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Locale locale = this.getResources().getConfiguration().getLocales().get(0);

        String country = locale.getCountry().toLowerCase();
        String countryName = locale.getDisplayCountry();

        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText("Latest News for " + countryName);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("apiKey").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                loadNews(String.valueOf(snapshot.getValue()), country);
            } else {
                Toast.makeText(getApplicationContext(), "Failed to get API key.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNews(String apiKey, String country) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPIService newsAPIService = retrofit.create(NewsAPIService.class);
        Call<AllNews> allNews = newsAPIService.getTopHeadlines(country, apiKey);

        allNews.enqueue(new Callback<AllNews>() {
            @Override
            public void onResponse(Call<AllNews> call, Response<AllNews> response) {
                adapter = new NewsAdapter(response.body().getNewsList());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AllNews> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "No news found", Toast.LENGTH_SHORT).show();
                Log.e("NewsError", throwable.getMessage());
            }
        });
    }
}