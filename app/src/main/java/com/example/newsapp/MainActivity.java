package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;



import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout=findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews("");
            }
        });

        //      progressBar=(ProgressBar)findViewById(R.id.progressBar);
        loadNews("");


/*

        adapter.setOnItemClickListener(new Adapter.RecyclerOnClickListener() {

            @Override
            public void onItemClick(int position) {

            }
        });*/
    }
   

    public void loadNews(String keyword) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI api = retrofit.create(JsonPlaceHolderAPI.class);

        Call<News> request;

        if(keyword.length()>0){
           request = api.getSearchNews(keyword,"de", "PubllishedAt","832b3be58d4d4bd19c10e8cbb099195e");
        }else {
            request = api.getNews("de", "832b3be58d4d4bd19c10e8cbb099195e");
        }
        request.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    List<Articles> articles = news.getArticles();
                    refreshLayout.setRefreshing(false);

                    Adapter adapter = new Adapter(MainActivity.this, articles);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);
                    //            progressBar.setVisibility(View.GONE);

                    adapter.setOnItemClickListener(new Adapter.RecyclerOnClickListener() {

                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                            if (!articles.isEmpty()) {
                                Bundle bundle = new Bundle();




                                String date=articles.get(position).getDate();
                                String title=articles.get(position).getTitle();
                                String urlToImage=articles.get(position).getUrlToImage();
                                String time=articles.get(position).getTime();
                                String url=articles.get(position).getUrl();
            //                    bundle.putSerializable("article", (Serializable) articles);

              //                  intent.putExtras(bundle);
                               intent.putExtra("date", (String) date);
                                intent.putExtra("title", (String) title);
                                intent.putExtra("urlToImage", (String) urlToImage);
                                intent.putExtra("time", (String) time);
                                intent.putExtra("url", (String) url);


                            }
                            //   intent.putExtra("position", (int) position);
                           startActivity(intent);
                        }

                    });

                }


            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("MYTAG", t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        SearchView searchView=(SearchView)  menu.findItem(R.id.search).getActionView();

       searchView.setQueryHint("Search news");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadNews(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadNews(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}


