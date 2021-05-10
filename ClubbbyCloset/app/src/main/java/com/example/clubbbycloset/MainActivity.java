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
    private static final String USERS_FILE = "allUsersData.txt";
    private String users = "laura.p:profilo1;elisa.a:profilo2;angelo.maria:profilo3;giorgidp:profilo2;giudc:profilo1;giacomo96:profilo3";

    private static final String TOPICS_FILE = "topics.txt";
    private String topics = "matrimonio/evento/occasione:img1;passegiata in compagnia/sport/amici/corsa:img2;aperitivo al mare/ape/amici/amiche/compagnia/appuntamento:img3;cena fuori/appuntamento/amici/ristorante:img4;università/scuola/liceo:img5;colloquio/lavoro/formale:img6;gita fuori porta/sportivo/amici/viaggio:img7;varie/cose:img8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignup = (Button) findViewById(R.id.btnsignup);
        Button btnLogin = (Button) findViewById(R.id.btnlogin);

        try {
            save(users, USERS_FILE);
            save(topics, TOPICS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, login.class);
                startActivity(login); // takes the user to the login activity

            }

        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, signup.class);
                startActivity(signup);
            }
        });

    }


    public void save(String text, String FILE_NAME) throws IOException {
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