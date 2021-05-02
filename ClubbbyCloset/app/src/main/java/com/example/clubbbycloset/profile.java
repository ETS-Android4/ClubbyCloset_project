package com.example.clubbbycloset;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class profile extends AppCompatActivity {
    private static final String FILE_NAME = "userdata.txt";
    ImageView bhome, bsearch, badd, bvote, bprofile, blogout;
    TextView tvusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);
        blogout = (ImageView)this.findViewById(R.id.logout);

        tvusername= (TextView)this.findViewById(R.id.username);
        try {
            tvusername.setText(load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(profile.this, home.class);
                startActivity(home); // takes the user to the signup activity
            }

        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(profile.this, search.class);
                startActivity(search); // takes the user to the signup activity
            }

        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(profile.this, profile.class);
                startActivity(profile); // takes the user to the signup activity
            }

        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(profile.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(profile.this, login.class);
                startActivity(login); // takes the user to the signup activity
            }

        });
    }

    public String load() throws IOException {
        FileInputStream fis = null;
        fis = openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;
        while ((text = br.readLine()) != null) {
            int i = 0;
            String[] t = text.split(";");
            while (t[i] != null){
                String[] s = t[i].split(":");
                if (s[0].equals("username")){
                    return s[1];
                }
                i++;
            }
        }
        return null;
    }

}