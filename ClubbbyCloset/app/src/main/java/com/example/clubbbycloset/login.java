package com.example.clubbbycloset;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class login extends AppCompatActivity {
    private static final String FILE_USER = "userdata.txt";
    Button b1;
    ImageButton b2;
    EditText ed1,ed2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = (Button)findViewById(R.id.btnlogin);
        ed1 = (EditText)findViewById(R.id.et_email);
        ed2 = (EditText)findViewById(R.id.et_password);
        b2 = (ImageButton)findViewById(R.id.newAccount);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ret = load();
                String name= null;
                String psw= null;
                int ok = -1;
                if (ret!=null){
                    String[] t = ret.split(";;");
                    for(int i =0; i<t.length; i++) {
                        name = t[i].split(";")[0].split(":")[1];
                        psw = t[i].split(";")[1].split(":")[1];
                        if (name.equals(ed1.getText().toString()) && psw.equals(ed2.getText().toString())) {
                            ok = 1;
                            Intent home = new Intent(login.this, home.class);
                            home.putExtra("idProfile", name);
                            startActivity(home); // takes the user to the signup activity
                        }
                    }
                    if(ok < 1) {
                        Toast.makeText(getApplicationContext(), "WrongCredentials", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "No Valid account please SIGN UP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(login.this, signup.class);
                startActivity(signup); // takes the user to the signup activity
            }

        });

    }

    public String load() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_USER);
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