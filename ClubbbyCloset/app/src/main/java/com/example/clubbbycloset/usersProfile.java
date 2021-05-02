package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class usersProfile extends AppCompatActivity {
    private static final String FILE_NAME = "allUsersData.txt";
    ImageView bhome, bsearch, badd, bvote, bprofile;
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

}