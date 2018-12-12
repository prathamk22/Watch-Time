package projects.pratham.movieinformation;

import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MovieInformation extends AppCompatActivity {

    String movieName;
    public static MovieInfo info;
    RelativeLayout mainLayout;
    ImageView imageThumbnail;
    EditText edit_query;
    CardView back;
    ProgressBar progressBar,imageLoader;
    boolean contains = false;
    final String url = "http://www.omdbapi.com/?apikey=42f1bf2&t=";
    TextView Plot,Actor,Director,production,language,rating,runtime,genre,released,fail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        progressBar = findViewById(R.id.progress);
        imageThumbnail = findViewById(R.id.imageThumbnail);
        fail = findViewById(R.id.fail);
        back = findViewById(R.id.back);
        edit_query = findViewById(R.id.edit_query);
        edit_query.clearFocus();
        Plot = findViewById(R.id.plot);
        released = findViewById(R.id.released);
        Actor = findViewById(R.id.actor);
        Director = findViewById(R.id.director);
        production = findViewById(R.id.production);
        language = findViewById(R.id.language);
        rating = findViewById(R.id.rating);
        runtime = findViewById(R.id.runTime);
        genre = findViewById(R.id.genre);
        imageLoader = findViewById(R.id.imageLoader);
        mainLayout = findViewById(R.id.mainLayout);
        movieName = getIntent().getStringExtra("name");
        edit_query.setText(movieName);
        edit_query.clearFocus();
        info = new MovieInfo();
        final AsyncTasks tasks = new AsyncTasks(url+movieName);
        tasks.execute();
        mainLayout.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieInformation.super.onBackPressed();
            }
        });

        final Handler handler = new Handler();
        new Runnable() {
            @Override
            public void run() {
                if(tasks.getStatus()== AsyncTask.Status.FINISHED){
                    if(!contains){
                        progressBar.setVisibility(View.INVISIBLE);
                        if(!info.getUrl().equals("")){
                            Glide.with(getApplicationContext()).load(info.getUrl()).addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    imageLoader.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(imageThumbnail);
                        }else{
                            fail.setVisibility(View.VISIBLE);
                            imageLoader.setVisibility(View.INVISIBLE);
                        }
                        edit_query.setText(info.getTitle());
                        edit_query.clearFocus();
                        released.setText(info.getReleased());
                        Plot.setText(info.getPlot());
                        Actor.setText(info.getActors());
                        Director.setText(info.getDirector());
                        production.setText(info.getProduction());
                        language.setText(info.getLang());
                        rating.setText(info.getRating());
                        runtime.setText(info.getRuntime());
                        genre.setText(info.getGenre());
                        if(Director.getText().equals(""))
                            Toast.makeText(MovieInformation.this, "No Movie Found with "+movieName, Toast.LENGTH_SHORT).show();
                        mainLayout.setVisibility(View.VISIBLE);
                        contains=true;
                        handler.removeCallbacks(this);
                    }
                }
                handler.postDelayed(this,1000);
            }
        }.run();
    }
}
