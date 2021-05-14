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
    private String users = "laura.p:profilo1:img1/img12/img22/img3/img4/img5/img14/img15;" +
            "elisa.a:profilo2:img4/img14/img22/img12/img15/img11/img3/img22;" +
            "giorgidp:profilo2:img2/img22/img1/img13/img14/img15/img21/img15;" +
            "giudc:profilo1:img3/img12/img22/img3/img4/img5/img14/img15;" +
            "giacomo96:profilo3:img23/img24/img25/img26/img23/img25/img24/img26;" +
            "angelo.maria:profilo3:img26/img25/img24/img23/img25/img26/img24/img23;";

    private static final String TOPICS_FILE = "topics.txt";
    private String topics = "matrimonio/evento/occasione:img1/img11/img12/img13/img14/img15/img16;" +
            "sport in compagnia/sport/amici/corsa/passeggiata:img2/img21/img22/img23/img24/img25/img26;" +
            "aperitivo al mare/ape/amici/amiche/compagnia/appuntamento:img3/img31/img32/img33/img34/img35/img36;" +
            "università/scuola/liceo:img4/img41/img42/img43/img44/img45/img46;" +
            "cena fuori/appuntamento/amici/ristorante:img5/img51/img52/img53/img54/img55/img56;" +
            "colloquio/lavoro/formale:img6/img61/img62/img63/img64/img65/img66;" +
            "gita fuori porta/sportivo/amici/viaggio:img7/img71/img72/img73/img74/img75/img76;" +
            "varie/cose:img8/img81/img82/img83/img84/img85/img86;";
    private static final String IMG_FILE = "images.txt";
    private String images="donna:img1/img11/img12/img13/img14/img15/img16img2/img21/img22;" +
            "uomo:img23/img24/img25/img26";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignup = (Button) findViewById(R.id.btnsignup);
        Button btnLogin = (Button) findViewById(R.id.btnlogin);

        try {
            save(users, USERS_FILE);
            save(topics, TOPICS_FILE);
            save(images, IMG_FILE);
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
        //Toast.makeText(getApplicationContext(), "Scritto "+text, Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}