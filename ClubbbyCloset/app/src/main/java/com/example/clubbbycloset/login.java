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
    private static final String FILE_NAME = "userdata.txt";
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

                if (ret!=null){
                    int i = 0;
                    String[] t = ret.split(";");
                    while (t.length > i ){
                        String[] s = t[i].split(":");
                        if (s[0].equals("username")){
                            name = s[1];
                            //Toast.makeText(getApplicationContext(), name,Toast.LENGTH_SHORT).show();
                        }else if (s[0].equals("password")){
                            psw = s[1];
                            //Toast.makeText(getApplicationContext(), psw,Toast.LENGTH_SHORT).show();
                        }
                        i++;
                    }
                    if(name.equals(ed1.getText().toString()) && psw.equals(ed2.getText().toString())) {
                        Intent home = new Intent(login.this, home.class);
                        startActivity(home); // takes the user to the signup activity
                    }else {
                        Toast.makeText(getApplicationContext(), "WrongCredentials",Toast.LENGTH_SHORT).show();
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