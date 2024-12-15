package com.example.newsapplicationmobile;

import android.os.Bundle;
import android.util.Log; // Import for logging
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private Spinner spinnerCategory;
    private String sc = "Everything";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewNews);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Spinner categorySpinner = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                sc = parent.getItemAtPosition(position).toString();
                //fetchNews();
                fetchNews(api, sc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


            // Setup Retrofit and API
            NewsApi api = RetrofitClient.getInstance().create(NewsApi.class);

            // Fetch default news on app launch


        });
    }

    private void fetchNews(NewsApi api, String category) {
        Log.d(TAG, "Fetching news for category: " + category);

        Call<NewsResponse> call = api.getNews(category, sc.toLowerCase(), "2024-11-15",  "bb7142f28f6e4a9fafed51939d47729f");
        Log.d(TAG, "API call URL: " + call.request().url());

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    Log.d(TAG, "API Response: Success, Articles Count: " + articles.size());
                    adapter = new NewsAdapter(articles);
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.e(TAG, "API Response: Failed, Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(MainActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
