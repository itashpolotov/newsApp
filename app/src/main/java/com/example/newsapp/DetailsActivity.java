package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       WebView webView;

        List<Articles> articles;
        ImageView newsImage;
        TextView title, time,date;
        ProgressBar progressBar;



         newsImage = (ImageView) findViewById(R.id.newsFoto);
         progressBar=(ProgressBar)findViewById(R.id.progressBar);
        title = (TextView) findViewById(R.id.title);
        time = (TextView) findViewById(R.id.time);
        date=(TextView)findViewById(R.id.date);
        Intent intent = getIntent();

        progressBar.setVisibility(View.VISIBLE);
        url=intent.getStringExtra("url");

      // articles = (List<Articles>) intent.getSerializableExtra("article");


        title.setText(intent.getStringExtra("title"));
        time.setText(intent.getStringExtra("time"));
        date.setText(intent.getStringExtra("date"));





        Picasso.get()
                .load(intent.getStringExtra("urlToImage"))
                .into(newsImage);


        webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        webView.loadUrl(url);

     webView.setWebViewClient(new WebViewClient(){
         @Override
         public void onPageFinished(WebView view, String url) {
             progressBar.setVisibility(View.INVISIBLE);
             super.onPageFinished(view, url);
         }
     });






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,url);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}