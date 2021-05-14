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
import java.util.Random;

public class usersProfile extends AppCompatActivity {
    private static final String FILE_NAME = "allUsersData.txt";
    ImageView bhome, bsearch, badd, bvote, bprofile, topicImg1,topicImg2, topicImg3, topicImg4, topicImg5, topicImg6, topicImg7, topicImg8;
    TextView name;
    ImageView profileImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        Bundle Extra = getIntent().getExtras();
        String textView = Extra.getString("user");
        //Toast.makeText(getApplicationContext(), "Nome "+textView, Toast.LENGTH_SHORT).show();

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        name = (TextView)this.findViewById(R.id.username);
        name.setText(textView);

        setProfileImg(textView);

        setPictures(textView);

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(usersProfile.this, home.class);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(usersProfile.this, search.class);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(usersProfile.this, profile.class);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(usersProfile.this, vote.class);
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

    public void setProfileImg(String textView){
        profileImg=(ImageView)this.findViewById(R.id.profile_img);
        String[] res = load().split(";");
        for(int i = 0 ; i<res.length; i++){
            if (res[i].split(":")[0].equals(textView)){
                String img = res[i].split(":")[1];
                //Toast.makeText(getApplicationContext(), "img src" + img, Toast.LENGTH_SHORT).show();
                int id = getResources().getIdentifier(img,"drawable", "com.example.clubbbycloset");
                profileImg.setBackgroundResource(id);
            }
        }
    }

    public void setPictures(String name){
        String[] res = load().split(";");
        for(int i = 0 ; i<res.length; i++) {
            if (res[i].split(":")[0].equals(name)) {
                String[] imgSrc = res[i].split(":")[2].split("/");

                if (imgSrc.length >= 8) {
                    //Toast.makeText(getApplicationContext(), "imags" + imgSrc[0] + "topics " + topic[0], Toast.LENGTH_SHORT).show();
                    topicImg1 = (ImageView) this.findViewById(R.id.topicimg1);
                    int id = getResources().getIdentifier(imgSrc[0], "drawable", "com.example.clubbbycloset");
                    topicImg1.setBackgroundResource(id);

                    topicImg2 = (ImageView) this.findViewById(R.id.topicimg2);
                    id = getResources().getIdentifier(imgSrc[1], "drawable", "com.example.clubbbycloset");
                    topicImg2.setBackgroundResource(id);

                    topicImg3 = (ImageView) this.findViewById(R.id.topicimg3);
                    id = getResources().getIdentifier(imgSrc[2], "drawable", "com.example.clubbbycloset");
                    topicImg3.setBackgroundResource(id);

                    topicImg4 = (ImageView) this.findViewById(R.id.topicimg4);
                    id = getResources().getIdentifier(imgSrc[3], "drawable", "com.example.clubbbycloset");
                    topicImg4.setBackgroundResource(id);

                    topicImg5 = (ImageView) this.findViewById(R.id.topicimg5);
                    id = getResources().getIdentifier(imgSrc[4], "drawable", "com.example.clubbbycloset");
                    topicImg5.setBackgroundResource(id);

                    topicImg6 = (ImageView) this.findViewById(R.id.topicimg6);
                    id = getResources().getIdentifier(imgSrc[5], "drawable", "com.example.clubbbycloset");
                    topicImg6.setBackgroundResource(id);

                    topicImg7 = (ImageView) this.findViewById(R.id.topicimg7);
                    id = getResources().getIdentifier(imgSrc[6], "drawable", "com.example.clubbbycloset");
                    topicImg7.setBackgroundResource(id);

                    topicImg8 = (ImageView) this.findViewById(R.id.topicimg8);
                    id = getResources().getIdentifier(imgSrc[7], "drawable", "com.example.clubbbycloset");
                    topicImg8.setBackgroundResource(id);
                }
            }
        }
    }
}