package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class search extends AppCompatActivity {
    private static final String FILE_NAME = "topics.txt";
    ImageView bhome, bsearch, badd, bvote, bprofile, topicImg1,  topicImg2,  topicImg3,  topicImg4,  topicImg5,  topicImg6,  topicImg7,  topicImg8;
    TextView t1,t2,t3,t4 ,t5, t6, t7, t8;
    EditText stopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        stopic = (EditText)this.findViewById(R.id.et_search);

        String[] res = load();
        String[] res2 = load();
        String[] imgSrc = res;
        String[] topic = res2;

        for( int i =0; i<res.length ; i++){
            //Toast.makeText(getApplicationContext(), "ciclo " + res[i] , Toast.LENGTH_SHORT).show();
            String[] item = res[i].split(":");
            String[] item2=  res2[i].split(":");
            //Toast.makeText(getApplicationContext(), "item[topic, img] " + item[0].split("/")[0] + "\n " + item2[1].split("/")[0] , Toast.LENGTH_SHORT).show();
            topic[i] = item[0].split("/")[0];
            imgSrc[i] = item2[1].split("/")[0];
            //Toast.makeText(getApplicationContext(), "dopo split\n img" + imgSrc[i] + " \ntopic " + topic[i], Toast.LENGTH_SHORT).show();
        }



        if (res.length >=8){
            //Toast.makeText(getApplicationContext(), "imags" + imgSrc[0] + "topics " + topic[0], Toast.LENGTH_SHORT).show();
            topicImg1 = (ImageView) this.findViewById(R.id.topicimg1);
            int id = getResources().getIdentifier(imgSrc[0],"drawable", "com.example.clubbbycloset");
            topicImg1.setBackgroundResource(id);

            topicImg2 = (ImageView) this.findViewById(R.id.topicimg2);
            id = getResources().getIdentifier(imgSrc[1],"drawable", "com.example.clubbbycloset");
            topicImg2.setBackgroundResource(id);

            topicImg3 = (ImageView) this.findViewById(R.id.topicimg3);
            id = getResources().getIdentifier(imgSrc[2],"drawable", "com.example.clubbbycloset");
            topicImg3.setBackgroundResource(id);

            topicImg4 = (ImageView) this.findViewById(R.id.topicimg4);
            id = getResources().getIdentifier(imgSrc[3],"drawable", "com.example.clubbbycloset");
            topicImg4.setBackgroundResource(id);

            topicImg5 = (ImageView) this.findViewById(R.id.topicimg5);
            id = getResources().getIdentifier(imgSrc[4],"drawable", "com.example.clubbbycloset");
            topicImg5.setBackgroundResource(id);

            topicImg6 = (ImageView) this.findViewById(R.id.topicimg6);
            id = getResources().getIdentifier(imgSrc[5],"drawable", "com.example.clubbbycloset");
            topicImg6.setBackgroundResource(id);

            topicImg7 = (ImageView) this.findViewById(R.id.topicimg7);
            id = getResources().getIdentifier(imgSrc[6],"drawable", "com.example.clubbbycloset");
            topicImg7.setBackgroundResource(id);

            topicImg8 = (ImageView) this.findViewById(R.id.topicimg8);
            id = getResources().getIdentifier(imgSrc[7],"drawable", "com.example.clubbbycloset");
            topicImg8.setBackgroundResource(id);

            t1 = (TextView)this.findViewById(R.id.t1);
            t1.setText(topic[0]);
            t2 = (TextView)this.findViewById(R.id.t2);
            t2.setText(topic[1]);
            t3 = (TextView)this.findViewById(R.id.t3);
            t3.setText(topic[2]);
            t4 = (TextView)this.findViewById(R.id.t4);
            t4.setText(topic[3]);
            t5 = (TextView)this.findViewById(R.id.t5);
            t5.setText(topic[4]);
            t6 = (TextView)this.findViewById(R.id.t6);
            t6.setText(topic[5]);
            t7 = (TextView)this.findViewById(R.id.t7);
            t7.setText(topic[6]);
            t8 = (TextView)this.findViewById(R.id.t8);
            t8.setText(topic[7]);

            topicImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t1.getText());
                }

            });

            topicImg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t2.getText());
                }

            });

            topicImg3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t4.getText());
                }

            });

            topicImg4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t4.getText());
                }

            });

            topicImg5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t5.getText());
                }

            });

            topicImg6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t6.getText());
                }

            });

            topicImg7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t7.getText());
                }

            });

            topicImg8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchActivity(t8.getText());
                }

            });


        }


        stopic.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String insert = String.valueOf(stopic.getText());
                    String[] forSearch = load();

                    for( int i =0; i<forSearch.length; i++){
                        //Toast.makeText(getApplicationContext(), "ciclo " + forSearch[i] , Toast.LENGTH_SHORT).show();
                        String items = forSearch[i].split(":")[0];
                        items.replace(" ", "");
                        //Toast.makeText(getApplicationContext(), "primo for " + items , Toast.LENGTH_SHORT).show();
                        String[] topics= items.split("/");
                        for(int j = 0 ; j<topics.length; j++){
                            //Toast.makeText(getApplicationContext(), "secondo for " + topics[j] +" "+ insert, Toast.LENGTH_SHORT).show();
                            if (topics[j].contains(insert)){
                                //Toast.makeText(getApplicationContext(), "Find in  " + items +"---"+ insert, Toast.LENGTH_SHORT).show();
                                switchActivity(insert);
                            }
                        }
                    }
                }
                return false;
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(search.this, home.class);
                startActivity(home); // takes the user to the signup activity
            }

        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(search.this, search.class);
                startActivity(search); // takes the user to the signup activity
            }

        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(search.this, profile.class);
                startActivity(profile); // takes the user to the signup activity
            }

        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(search.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });
    }

    public String[] load() {
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
            //Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
            return sb.toString().split(";");
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

    public void switchActivity(CharSequence topic){
        Intent profilo = new Intent(search.this, searchResults.class);
        profilo.putExtra("categorie", topic);
        startActivity(profilo);
    }

}