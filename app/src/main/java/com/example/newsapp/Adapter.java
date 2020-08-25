package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.SampleHolder> {

   List<Articles> articles;
    Context context; //я мог пользоваться привилегиями Activity
    RecyclerOnClickListener listener;

    public Adapter(Context context, List<Articles> articles) {
        this.articles = articles;
        this.context = context;
    }

    public void setOnItemClickListener(RecyclerOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SampleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news, parent, false); //use activity_meal

        return new SampleHolder(view, listener);
    }

    //процесс соединения данных с макетом
    @Override
    public void onBindViewHolder(@NonNull SampleHolder holder, int position) {

        holder.author.setText(articles.get(position).getAuthor());
        holder.date.setText(articles.get(position).getDate());
        holder.title.setText(articles.get(position).getTitle());
        holder.desc.setText(articles.get(position).getDesc());
        holder.source.setText(articles.get(position).getSource().getName());
        holder.time.setText(articles.get(position).getTime());

        Picasso.get()
                .load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.bottom_shadow)
                .error(R.drawable.bottom_shadow)
                .into(holder.newsImage);

    }

    //узнает сколько блоков в итоге у нас будет
    @Override
    public int getItemCount() {
        return articles.size();
    }

    //данный класс позволяет нам сопоставить данные с View элементами activity_meal.xml
    public static class SampleHolder extends RecyclerView.ViewHolder {

        ImageView newsImage;
        TextView author;
        TextView date;
        TextView  title;
        TextView desc;
        TextView source;
        TextView time;


        public SampleHolder(@NonNull View itemView, final RecyclerOnClickListener listener) {
            super(itemView); //example;


            newsImage=(ImageView) itemView.findViewById(R.id.newsFoto);
            author=(TextView)itemView.findViewById(R.id.author);
            date=(TextView)itemView.findViewById(R.id.date);
            title=(TextView)itemView.findViewById(R.id.title);
            desc=(TextView)itemView.findViewById(R.id.desc);
            source=(TextView)itemView.findViewById(R.id.source);
            time=(TextView)itemView.findViewById(R.id.time);



            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });

        }
    }


    public interface RecyclerOnClickListener {
        void onItemClick(int position);


    }


}
