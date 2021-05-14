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

public class profile extends AppCompatActivity {
    private static final String FILE_USER = "userdata.txt";
    private static final String FILE_IMG = "images.txt";
    ImageView bhome, bsearch, badd, bvote, bprofile, blogout, topicImg1,topicImg2, topicImg3, topicImg4, topicImg5, topicImg6, topicImg7, topicImg8;
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
            tvusername.setText(load(FILE_USER));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setImages();
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

    public String load(String FILE_NAME) throws IOException {
        FileInputStream fis = null;
        fis = openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;
        while ((text = br.readLine()) != null) {
            if(FILE_NAME.equals(FILE_USER)){
                String[] t =text.split(";");
                for (int i =0; t[i] != null; i++){
                    String[] s = t[i].split(":");
                    if (s[0].equals("username")){
                        return s[1];
                    }
                }
            }
            else {
                return text;
            }
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

    public void setImages() throws IOException {
        String resd = load(FILE_IMG).split(";")[0].split(":")[1];
        String resu = load(FILE_IMG).split(";")[1].split(":")[1];
        String aux = resd + "/" + resu;
        String[] imgSrc = aux.split("/");
        int size = imgSrc.length;
        if (imgSrc.length >=8){
            //Toast.makeText(getApplicationContext(), "imags" + imgSrc[0] + "topics " + topic[0], Toast.LENGTH_SHORT).show();
            topicImg1 = (ImageView) this.findViewById(R.id.topicimg1);
            int id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg1.setBackgroundResource(id);

            topicImg2 = (ImageView) this.findViewById(R.id.topicimg2);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg2.setBackgroundResource(id);

            topicImg3 = (ImageView) this.findViewById(R.id.topicimg3);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg3.setBackgroundResource(id);

            topicImg4 = (ImageView) this.findViewById(R.id.topicimg4);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg4.setBackgroundResource(id);

            topicImg5 = (ImageView) this.findViewById(R.id.topicimg5);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg5.setBackgroundResource(id);

            topicImg6 = (ImageView) this.findViewById(R.id.topicimg6);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg6.setBackgroundResource(id);

            topicImg7 = (ImageView) this.findViewById(R.id.topicimg7);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg7.setBackgroundResource(id);

            topicImg8 = (ImageView) this.findViewById(R.id.topicimg8);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg8.setBackgroundResource(id);

        }

    }


}