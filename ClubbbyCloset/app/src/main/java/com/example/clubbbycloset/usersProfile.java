package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class usersProfile extends AppCompatActivity {
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        Bundle Extra = getIntent().getExtras();
        String textView = Extra.getString("user");
        Toast.makeText(getApplicationContext(), "Nome "+textView, Toast.LENGTH_SHORT).show();
        name = (TextView)this.findViewById(R.id.username);
        name.setText(textView);

    }
}