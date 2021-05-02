package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class searchResults extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile;
    TextView tv1,tv2,tv3,tvTitolo;
    private static final String FILE_NAME = "allUsersData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        String res = load();
        String[] l = res.split(";");
        String[] users =new String[l.length];

        for(int i = 0; i<l.length; i++){
            users[i] = l[i].split(":")[0];
            //Toast.makeText(getApplicationContext(), users[i], Toast.LENGTH_SHORT).show();
        }

        Bundle Extra = getIntent().getExtras();
        String textView = Extra.getString("categorie");
        Toast.makeText(getApplicationContext(), "Nome "+textView, Toast.LENGTH_SHORT).show();

        tvTitolo =(TextView)this.findViewById(R.id.categoria);
        tvTitolo.setText(textView);

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        tv1 = (TextView)this.findViewById(R.id.home1);
        tv1.setText(users[new Random().nextInt(l.length)]);

        tv2 = (TextView)this.findViewById(R.id.home2);
        tv2.setText(users[new Random().nextInt(l.length)]);

        tv3 = (TextView)this.findViewById(R.id.home3);
        tv3.setText(users[new Random().nextInt(l.length)]);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(searchResults.this, usersProfile.class);
                profilo.putExtra("user", tv1.getText());
                startActivity(profilo);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(searchResults.this, usersProfile.class);
                profilo.putExtra("user", tv2.getText());
                startActivity(profilo);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(searchResults.this, usersProfile.class);
                profilo.putExtra("user", tv3.getText());
                startActivity(profilo);
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(searchResults.this, home.class);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(searchResults.this, search.class);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(searchResults.this, profile.class);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(searchResults.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

    }

    public String load() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}