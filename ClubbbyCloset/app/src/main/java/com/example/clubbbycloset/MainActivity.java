package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
    private String topics = "topic:matrimonio:evento:occasione;imgSrc:img1:img11:img12:img13:img14:img15:img16;;" +
            "topic:sport in compagnia:sport:amici:corsa:passeggiata;imgSrc:img2:img21:img22:img23:img24:img25:img26;;" +
            "topic:aperitivo al mare:ape:amici:amiche:compagnia:appuntamento;imgSrc:img3:img31:img32:img33:img34:img35:img36;;" +
            "topic:università:scuola:liceo;imgSrc:img4:img41:img42:img43:img44:img45:img46;;" +
            "topic:cena fuori:appuntamento:amici:ristorante;imgSrc:img5:img51:img52:img53:img54:img55:img56;;" +
            "topic:colloquio:lavoro:formale;imgSrc:img6:img61:img62:img63:img64:img65:img66;;" +
            "topic:gita fuori porta:sportivo:amici:viaggio;imgSrc:img7:img71:img72:img73:img74:img75:img76;;" +
            "topic:varie:cose;imgSrc:img8:img81:img82:img83:img84:img85:img86;;";

    private  static final String FILE_ALLVOTE = "allVote.txt";
    private String votes = "username:giudc;voteSrc:img11:img12;description:provasedcrizione:provalovation:provaorario;vote:0:0;;" +
            "username:angelo.maria;voteSrc:img24:img25;description:provasedcrizione1:provalovation1:provaorario1;vote:0:0;;" +
            "username:angelo.maria;voteSrc:img25:img24;description:provasedcrizione1:provalovation1:provaorario1;vote:0:0;;" +
            "username:elisa.a;voteSrc:img21:img22;description:provasedcrizione2:provalovation2:provaorario2;vote:0:0;;" +
            "username:elisa.a;voteSrc:img22:img21;description:provasedcrizione2:provalovation2:provaorario2;vote:0:0;;" +
            "username:giacomo96;voteSrc:img26:img25;description:provasedcrizione3:provalovation3:provaorario3;vote:0:0;;"+
            "username:giacomo96;voteSrc:img25:img26;description:provasedcrizione3:provalovation3:provaorario3;vote:0:0;;";

    private static final String FILE_USER = "userdata.txt";
    Button b1;
    TextView b2,forgot;
    EditText ed1,ed2;
    CheckBox show_hide_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.btnlogin);
        ed1 = (EditText)findViewById(R.id.et_email);
        ed2 = (EditText)findViewById(R.id.et_password);
        b2 = (TextView) findViewById(R.id.newAccount);
        forgot = (TextView) findViewById(R.id.forgot);
        show_hide_password = (CheckBox) this.findViewById(R.id.show_hide_password);

        try {
            saveFile(users, FILE_ALLUSERS);
            saveFile(topics, FILE_TOPICS);
            saveFile(votes, FILE_ALLVOTE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ret = load(FILE_USER);
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
                            Intent home = new Intent(MainActivity.this, home.class);
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
                Intent signup = new Intent(MainActivity.this, signup.class);
                startActivity(signup); // takes the user to the signup activity
            }

        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_forgot, null);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

            }
        });

        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    ed2.setInputType(InputType.TYPE_CLASS_TEXT);
                    ed2.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    ed2.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ed2.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password

                }

            }
        });

    }

    public void saveFile(String txt,String FILE_NAME) throws IOException {
        String res = load(FILE_NAME);
        if (res == null){
            save(txt, FILE_NAME);
        }else {
            save(res,FILE_NAME);
        }
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

    public String load(String FILE_NAME) {
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