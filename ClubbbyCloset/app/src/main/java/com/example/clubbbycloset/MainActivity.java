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
    private static final String FILE_ALLUSERS = "allUsersData.txt";
    private String users = "laura.p:profilo1;imgSrc:img1;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "laura.p:profilo1;imgSrc:img12;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "laura.p:profilo1;imgSrc:img22;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "laura.p:profilo1;imgSrc:img3;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "laura.p:profilo1;imgSrc:img4;descrizione:provadescri:provalocation:provaora:provalink;;" +
            "elisa.a:profilo2;imgSrc:img4;descrizione:provadescri1:provalocation1:provaora1:provalink1;;" +
            "elisa.a:profilo2;imgSrc:img14;descrizione:provadescri1:provalocation1:provaora1:provalink1;;" +
            "elisa.a:profilo2;imgSrc:img22;descrizione:provadescri1:provalocation1:provaora1:provalink1;;" +
            "elisa.a:profilo2;imgSrc:img12;descrizione:provadescri1:provalocation1:provaora1:provalink1;;" +
            "elisa.a:profilo2;imgSrc:img15;descrizione:provadescri1:provalocation1:provaora1:provalink1;;" +
            "giorgidp:profilo2;imgSrc:img2;descrizione:provadescri2:provalocation2:provaora2:provalink2;;" +
            "giorgidp:profilo2;imgSrc:img22;descrizione:provadescri2:provalocation2:provaora2:provalink2;;" +
            "giorgidp:profilo2;imgSrc:img1;descrizione:provadescri2:provalocation2:provaora2:provalink2;;" +
            "giorgidp:profilo2;imgSrc:img13;descrizione:provadescri2:provalocation2:provaora2:provalink2;;" +
            "giudc:profilo1;imgSrc:img3;descrizione:provadescri3:provalocation3:provaora3:provalink3;;" +
            "giudc:profilo1;imgSrc:img12;descrizione:provadescri3:provalocation3:provaora3:provalink3;;" +
            "giudc:profilo1;imgSrc:img22;descrizione:provadescri3:provalocation3:provaora3:provalink3;;" +
            "giudc:profilo1;imgSrc:img4;descrizione:provadescri3:provalocation3:provaora3:provalink3;;" +
            "giudc:profilo1;imgSrc:img15;descrizione:provadescri3:provalocation3:provaora3:provalink3;;" +
            "giacomo96:profilo3;imgSrc:img23;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "giacomo96:profilo3;imgSrc:img24;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "giacomo96:profilo3;imgSrc:img25;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "giacomo96:profilo3;imgSrc:img26;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "giacomo96:profilo3;imgSrc:img23;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "angelo.maria:profilo3;imgSrc:img26;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "angelo.maria:profilo3;imgSrc:img25;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "angelo.maria:profilo3;imgSrc:img23;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "angelo.maria:profilo3;imgSrc:img24;descrizione:provadescri4:provalocation4:provaora4:provalink4;;" +
            "angelo.maria:profilo3;imgSrc:img23;descrizione:provadescri4:provalocation4:provaora4:provalink4;;";

    private static final String FILE_TOPICS = "topics.txt";
    private String topics = "matrimonio/evento/occasione:img1/img11/img12/img13/img14/img15/img16;" +
            "sport in compagnia/sport/amici/corsa/passeggiata:img2/img21/img22/img23/img24/img25/img26;" +
            "aperitivo al mare/ape/amici/amiche/compagnia/appuntamento:img3/img31/img32/img33/img34/img35/img36;" +
            "università/scuola/liceo:img4/img41/img42/img43/img44/img45/img46;" +
            "cena fuori/appuntamento/amici/ristorante:img5/img51/img52/img53/img54/img55/img56;" +
            "colloquio/lavoro/formale:img6/img61/img62/img63/img64/img65/img66;" +
            "gita fuori porta/sportivo/amici/viaggio:img7/img71/img72/img73/img74/img75/img76;" +
            "varie/cose:img8/img81/img82/img83/img84/img85/img86;";
    private  static final String FILE_ALLVOTE = "allVote.txt";
    private String votes = "username:giudc;voteSrc:img11:img12;description:provasedcrizione:provalovation:provaorario;vote:0:0;;" +
            "username:angelo.maria;voteSrc:img24:img25;description:provasedcrizione1:provalovation1:provaorario1;vote:0:0;;" +
            "username:angelo.maria;voteSrc:img25:img24;description:provasedcrizione1:provalovation1:provaorario1;vote:0:0;;" +
            "username:elisa.a;voteSrc:img21:img22;description:provasedcrizione2:provalovation2:provaorario2;vote:0:0;;" +
            "username:elisa.a;voteSrc:img22:img21;description:provasedcrizione2:provalovation2:provaorario2;vote:0:0;;" +
            "username:giacomo96;voteSrc:img26:img25;description:provasedcrizione3:provalovation3:provaorario3;vote:0:0;;"+
            "username:giacomo96;voteSrc:img25:img26;description:provasedcrizione3:provalovation3:provaorario3;vote:0:0;;";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignup = (Button) findViewById(R.id.btnsignup);
        Button btnLogin = (Button) findViewById(R.id.btnlogin);

        try {
            save(users, FILE_ALLUSERS);
            save(topics, FILE_TOPICS);
            save(votes, FILE_ALLVOTE);
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