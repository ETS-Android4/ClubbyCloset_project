package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "allUsersData.txt";
    private String users = "laura.p:profilo1;elisa.a:profilo2;angelo.maria:profilo3;giorgidp:profilo2;giudc:profilo1;giacomo96:profilo3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignup = (Button) findViewById(R.id.btnsignup);
        Button btnLogin = (Button) findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    save(users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent login = new Intent(MainActivity.this, login.class);
                startActivity(login); // takes the user to the login activity

            }

        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    save(users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent signup = new Intent(MainActivity.this, signup.class);
                startActivity(signup);
            }
        });

    }


    public void save(String text) throws IOException {
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(text.getBytes());
        Toast.makeText(getApplicationContext(), "Scritto "+text, Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}