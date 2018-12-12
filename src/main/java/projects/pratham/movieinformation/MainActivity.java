package projects.pratham.movieinformation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edit_query;
    Button btn;
    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_query = findViewById(R.id.edit_query);
        head = findViewById(R.id.head);
        btn = findViewById(R.id.btn);
        Typeface typeface = Typeface.create("fonts/advento.ttf", Typeface.NORMAL);
        head.setTypeface(typeface);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_query.getText().toString().matches("")){
                    Intent intent = new Intent(getApplicationContext(), MovieInformation.class);
                    intent.putExtra("name", edit_query.getText().toString());
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity)MainActivity.this,(View)edit_query,"EditText");
                    startActivity(intent,optionsCompat.toBundle());
                }else{
                    Toast.makeText(MainActivity.this, "Enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
