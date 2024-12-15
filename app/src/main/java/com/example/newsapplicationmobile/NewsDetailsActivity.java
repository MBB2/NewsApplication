package com.example.newsapplicationmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    private String newsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView title = findViewById(R.id.textViewTitle);
        TextView description = findViewById(R.id.textViewDescription);
        ImageView image = findViewById(R.id.imageViewNews);

        Intent intent = getIntent();
        newsTitle = intent.getStringExtra("title");
        title.setText(newsTitle);
        description.setText(intent.getStringExtra("description"));
        Picasso.get().load(intent.getStringExtra("image")).into(image);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            // Share the news
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, newsTitle);
            startActivity(Intent.createChooser(shareIntent, "Share news via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
