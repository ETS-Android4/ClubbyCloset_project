package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class usersProfile extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile, topicImg1,topicImg2, topicImg3, topicImg4, topicImg5, topicImg6, topicImg7, topicImg8;
    TextView name;
    ImageView profileImg;

    private static final String FILE_ALLUSERS = "allUsersData.txt";

    private static final String FILE_USERVOTE ="uservote.txt";
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;
    private static final String FILE_USERIMG = "userimg.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ArrayList<Uri> mArrayUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        Bundle Extra = getIntent().getExtras();
        String textView = Extra.getString("user");
        //Toast.makeText(getApplicationContext(), "Nome "+textView, Toast.LENGTH_SHORT).show();

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        name = (TextView)this.findViewById(R.id.username);
        name.setText(textView);

        setProfileImg(textView);

        setPictures(textView);

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(usersProfile.this, home.class);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(usersProfile.this, search.class);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(usersProfile.this, profile.class);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(usersProfile.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(usersProfile.this, badd);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(home.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Add new img")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        } if (item.getTitle().equals("Add new vote")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(i, RESULT_LOAD_VOTE);
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

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

    public void setProfileImg(String textView){
        profileImg=(ImageView)this.findViewById(R.id.profile_img);
        String[] res = load(FILE_ALLUSERS).split(";");
        for(int i = 0 ; i<res.length; i++){
            if (res[i].split(":")[0].equals(textView)){
                String img = res[i].split(":")[1];
                //Toast.makeText(getApplicationContext(), "img src" + img, Toast.LENGTH_SHORT).show();
                int id = getResources().getIdentifier(img,"drawable", "com.example.clubbbycloset");
                profileImg.setBackgroundResource(id);
            }
        }
    }

    public void setPictures(String name){
        String[] res = load(FILE_ALLUSERS).split(";");
        for(int i = 0 ; i<res.length; i++) {
            if (res[i].split(":")[0].equals(name)) {
                String[] imgSrc = res[i].split(":")[2].split("/");

                if (imgSrc.length >= 8) {
                    //Toast.makeText(getApplicationContext(), "imags" + imgSrc[0] + "topics " + topic[0], Toast.LENGTH_SHORT).show();
                    topicImg1 = (ImageView) this.findViewById(R.id.topicimg1);
                    int id = getResources().getIdentifier(imgSrc[0], "drawable", "com.example.clubbbycloset");
                    topicImg1.setBackgroundResource(id);

                    topicImg2 = (ImageView) this.findViewById(R.id.topicimg2);
                    id = getResources().getIdentifier(imgSrc[1], "drawable", "com.example.clubbbycloset");
                    topicImg2.setBackgroundResource(id);

                    topicImg3 = (ImageView) this.findViewById(R.id.topicimg3);
                    id = getResources().getIdentifier(imgSrc[2], "drawable", "com.example.clubbbycloset");
                    topicImg3.setBackgroundResource(id);

                    topicImg4 = (ImageView) this.findViewById(R.id.topicimg4);
                    id = getResources().getIdentifier(imgSrc[3], "drawable", "com.example.clubbbycloset");
                    topicImg4.setBackgroundResource(id);

                    topicImg5 = (ImageView) this.findViewById(R.id.topicimg5);
                    id = getResources().getIdentifier(imgSrc[4], "drawable", "com.example.clubbbycloset");
                    topicImg5.setBackgroundResource(id);

                    topicImg6 = (ImageView) this.findViewById(R.id.topicimg6);
                    id = getResources().getIdentifier(imgSrc[5], "drawable", "com.example.clubbbycloset");
                    topicImg6.setBackgroundResource(id);

                    topicImg7 = (ImageView) this.findViewById(R.id.topicimg7);
                    id = getResources().getIdentifier(imgSrc[6], "drawable", "com.example.clubbbycloset");
                    topicImg7.setBackgroundResource(id);

                    topicImg8 = (ImageView) this.findViewById(R.id.topicimg8);
                    id = getResources().getIdentifier(imgSrc[7], "drawable", "com.example.clubbbycloset");
                    topicImg8.setBackgroundResource(id);
                }
            }
        }
    }

    //to load img from gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyStoragePermissions(this);
        if (requestCode == RESULT_LOAD_IMAGE) {
            newImg(requestCode,resultCode,data);
        }else if(requestCode == RESULT_LOAD_VOTE) {
            try {
                newVote(requestCode,resultCode,data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newVote(int requestCode, int resultCode, Intent data) throws IOException {
        if (resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                String paths = "";
                int cout = data.getClipData().getItemCount();
                Toast.makeText(getApplicationContext(), "SIZE  " + cout,Toast.LENGTH_SHORT).show();
                if(cout <= 4) {
                    for (int i = 0; i < cout; i++) {
                        // adding imageuri in array
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        paths = paths + picturePath + ":";
                        //Toast.makeText(getApplicationContext(), "LETTO  " + paths,Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                    loadVoteImg(paths, FILE_USERVOTE);
                    Intent voteView = new Intent(usersProfile.this, voteView.class);
                    voteView.putExtra("numb", "0");
                    startActivity(voteView);
                }
            }

        }
    }

    private void loadVoteImg(String picPath, String FILE_NAME) throws IOException {
        //Toast.makeText(getApplicationContext(), "LETTO  " + load(FILE_NAME),Toast.LENGTH_SHORT).show();
        String t = load(FILE_NAME) + ";voteSrc:" + picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void newImg(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            try {
                changeProfileImg(picturePath, FILE_USERIMG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursor.close();
        }
    }

    //to give the permission for load img
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void changeProfileImg(String picPath, String FILE_NAME) throws IOException {
        //Toast.makeText(getApplicationContext(), "LETTO  " + load(FILE_NAME),Toast.LENGTH_SHORT).show();
        String t =load(FILE_NAME)+":"+picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}