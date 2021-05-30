package com.example.clubbbycloset;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;


public class signup extends AppCompatActivity {
    private static final String FILE_USER = "userdata.txt";
    private static final String FILE_IMG = "userimg.txt";

    Button b1;
    TextView b2;
    EditText ed2,ed3,ed4;
    String username;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        b1 = (Button)findViewById(R.id.btn_register);
        ed2 = (EditText)findViewById(R.id.et_email);
        ed3 = (EditText)findViewById(R.id.et_password);
        ed4 = (EditText)findViewById(R.id.et_repassword);
        b2 = (TextView) this.findViewById(R.id.Accedi);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmail(ed2)){
                    Toast.makeText(getApplicationContext(), "Wrong Mail",Toast.LENGTH_SHORT).show();

                }else if(ed3.getText().toString().equals( ed4.getText().toString()) ) {
                    Resources res = getResources();
                    String[] email = ed2.getText().toString().split("@");
                    String psw = ed3.getText().toString();
                    username = email[0];
                    Toast.makeText(getApplicationContext(),
                            "Registered "+ username ,Toast.LENGTH_SHORT).show();
                    try {
                        save(FILE_USER, "username:"+username+";password:"+psw+";profileImg");
                        save(FILE_IMG, "username:"+username+";imgSrc");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent login = new Intent(signup.this, home.class);
                    startActivity(login); // takes the user to the signup activity

                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(signup.this, login.class);
                startActivity(login); // takes the user to the signup activity
            }
        });
    }



    public void save(String FILE_NAME, String text) throws IOException {
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(text.getBytes());
        ed2.getText().clear();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}