package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class search extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile, cat1, cat2, cat3, cat4;
    TextView t1,t2,t3,t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        cat1 = (ImageView) this.findViewById(R.id.cat1);
        cat2 = (ImageView) this.findViewById(R.id.cat2);
        cat3 = (ImageView) this.findViewById(R.id.cat3);
        cat4 = (ImageView) this.findViewById(R.id.cat4);

        t1 = (TextView)this.findViewById(R.id.t1);
        t2 = (TextView)this.findViewById(R.id.t2);
        t3 = (TextView)this.findViewById(R.id.t3);
        t4 = (TextView)this.findViewById(R.id.t4);

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(search.this, searchResults.class);
                profilo.putExtra("categorie", t1.getText());
                startActivity(profilo);
            }

        });

        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(search.this, searchResults.class);
                profilo.putExtra("categorie", t2.getText());
                startActivity(profilo);
            }

        });

        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(search.this, searchResults.class);
                profilo.putExtra("categorie", t3.getText());
                startActivity(profilo);
            }

        });

        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(search.this, searchResults.class);
                profilo.putExtra("categorie", t4.getText());
                startActivity(profilo);
            }

        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(search.this, home.class);
                startActivity(home); // takes the user to the signup activity
            }

        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(search.this, search.class);
                startActivity(search); // takes the user to the signup activity
            }

        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(search.this, profile.class);
                startActivity(profile); // takes the user to the signup activity
            }

        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(search.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });
    }
}